package de.sharetrip.core.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.sharetrip.core.security.user.CustomUserDetails;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class TokenProvider {

    @Value("${jwt.secret}")
    private String clientSecret;

    @Getter
    @Value("${jwt.expirationInMsec}")
    private int tokenExpiration;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC512(clientSecret);
    }

    public String createToken(final CustomUserDetails customUserDetails) {

        final Date date = new Date();
        final Date tokenExpirationDate = DateUtils.addMilliseconds(date, tokenExpiration);

        return JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(customUserDetails.getUsername())
                .withExpiresAt(tokenExpirationDate)
                .sign(algorithm);
    }

    public boolean validate(final String token,
                            final CustomUserDetails customUserDetails) {
        try {
            final DecodedJWT decodedJWT = this.decode(token);
            return verifyToken(decodedJWT, customUserDetails);
        } catch (final JWTVerificationException exception) {
            log.error("Could not verify JWT [%s]", token);
        }
        return false;
    }

    private DecodedJWT decode(final String token) {
        final JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    private boolean verifyToken(final DecodedJWT decodedJWT,
                                final CustomUserDetails customUserDetails) {

        return customUserDetails
                .getUsername()
                .equals(decodedJWT.getSubject());
    }
}
