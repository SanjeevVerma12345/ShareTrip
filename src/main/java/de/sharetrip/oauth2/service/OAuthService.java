package de.sharetrip.oauth2.service;

import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.core.security.TokenProvider;
import de.sharetrip.core.security.user.CustomUserDetails;
import de.sharetrip.oauth2.dto.AuthorizeDto;
import de.sharetrip.oauth2.dto.OAuth2Response;
import de.sharetrip.oauth2.utility.FirebaseService;
import de.sharetrip.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OAuthService {

    private final TokenProvider tokenProvider;

    public OAuth2Response prepareOAuthResponse(final User user,
                                               final AuthorizeDto authorizeDto) throws UserNotAuthorizedException {

        final String emailId = authorizeDto.getEmailId();
        final String idToken = authorizeDto.getIdToken();

        FirebaseService.getFirebaseToken(idToken, emailId);

        final String jsonWebToken = prepareJsonWebToken(user);
        final int tokenExpiration = tokenProvider.getTokenExpiration();

        return new OAuth2Response(
                jsonWebToken,
                tokenExpiration);
    }

    private String prepareJsonWebToken(final User user) {

        final CustomUserDetails customUserDetails = CustomUserDetails
                .builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .accountNonLocked(user.isAccountNonLocked())
                .enabled(user.isEnabled())
                .authenticationProvider(user.getAuthenticationProvider().name())
                .build();

        return tokenProvider.createToken(customUserDetails);
    }
}
