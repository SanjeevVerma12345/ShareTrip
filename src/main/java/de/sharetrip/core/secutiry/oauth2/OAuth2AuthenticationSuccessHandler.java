package de.sharetrip.core.secutiry.oauth2;

import de.sharetrip.core.config.AppProperties;
import de.sharetrip.core.exception.BadRequestException;
import de.sharetrip.core.secutiry.TokenProvider;
import de.sharetrip.core.utility.CookieUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static de.sharetrip.core.secutiry.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@AllArgsConstructor
@Slf4j
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    private final AppProperties appProperties;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private static final String BAD_REQUEST_EXCEPTION_MESSAGE = "We've got an Unauthorized Redirect URI and can't proceed with the authentication";

    private static final String QUERY_PARAM_TOKEN = "token";

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) throws IOException {

        final String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            final String message = String.format("Response has already been committed. Unable to redirect to [%s]", targetUrl);
            log.debug(message);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication) {

        final Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException(BAD_REQUEST_EXCEPTION_MESSAGE);
        }

        final String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        final String token = tokenProvider.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam(QUERY_PARAM_TOKEN, token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(final HttpServletRequest request, final HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(final String uri) {
        final URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    final URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost()
                            .equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }
}
