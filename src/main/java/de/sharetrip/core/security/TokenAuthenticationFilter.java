package de.sharetrip.core.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.BadRequestException;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.core.security.user.CustomUserDetails;
import de.sharetrip.core.security.user.repository.CustomUserDetailsRepository;
import de.sharetrip.core.util.RequestHeaderUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final CustomUserDetailsRepository customUserDetailsRepository;

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException, ServletException {

        try {
            final String token = RequestHeaderUtility.getJwtFromRequest(request);
            final String userName = RequestHeaderUtility.getUserFromRequest(request);
            final CustomUserDetails customUserDetails = customUserDetailsRepository.getUserDetailsByUserName(userName);

            final boolean isTokenValid = tokenProvider.validate(token, customUserDetails);

            if (isTokenValid) {
                assignUserToCurrentSession(customUserDetails);
            } else {
                throw new UserNotAuthorizedException();
            }
        } catch (final BadRequestException | JWTVerificationException e) {
            log.error("Could not verify request", e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (final UserNotAuthorizedException | AccountLockedException ex) {
            log.error("User account is either locked or user not authorized", ex);
            response.sendError(ex.getHttpStatus().value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void assignUserToCurrentSession(final CustomUserDetails customUserDetails) {

        final Authentication authentication = new UsernamePasswordAuthenticationToken(
                customUserDetails,
                customUserDetails.getPassword(),
                customUserDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
