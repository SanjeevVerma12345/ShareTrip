package de.sharetrip.core.secutiry;

import de.sharetrip.core.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@AllArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;

    public String createToken(final Authentication authentication) {
        final UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        final Date now = new Date();
        final String expiration = StringUtils.join(now.getTime(), appProperties.getAuth().getTokenExpirationMsec());
        final Date expiryDate = new Date(expiration);

        return Jwts.builder()
                .setSubject(Long.toString(userPrincipal.getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Long getUserIdFromToken(final String token) {
        final Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(final String authToken) {
        try {
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        } catch (final SignatureException ex) {
            log.error("Invalid JWT signature");
        } catch (final MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (final ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (final UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (final IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return false;
    }
}
