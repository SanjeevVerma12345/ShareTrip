package de.sharetrip.core.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import de.sharetrip.core.exception.BadRequestException;
import de.sharetrip.core.security.user.CustomUserDetails;
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

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws IOException, ServletException {

        try {

            final String token = RequestHeaderUtility.getJwtFromRequest(request);
            final String user = RequestHeaderUtility.getUserFromRequest(request);
            final CustomUserDetails customUserDetails = new CustomUserDetails(user);

            tokenProvider.validate(token, customUserDetails);
            assignUserToCurrentSession(customUserDetails);

        } catch (final BadRequestException | JWTVerificationException ex) {
            log.debug("Could not verify request", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void assignUserToCurrentSession(final CustomUserDetails customUserDetails) {

        final Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

}
