package de.sharetrip.core.util;

import de.sharetrip.core.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@UtilityClass
public class RequestHeaderUtility {

    private static final String BEARER_TYPE = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_USER = "user";

    public static String getJwtFromRequest(final HttpServletRequest request) {

        final String bearerToken = request.getHeader(HEADER_AUTHORIZATION);

        return Optional.of(bearerToken)
                       .filter(StringUtils::isNotBlank)
                       .filter(token -> token.startsWith(BEARER_TYPE))
                       .map(token -> token.substring(7))
                       .orElseThrow(BadRequestException::new);
    }

    public static String getUserFromRequest(final HttpServletRequest request) {

        final String bearerToken = request.getHeader(HEADER_USER);

        return Optional.of(bearerToken)
                       .filter(StringUtils::isNotBlank)
                       .orElseThrow(BadRequestException::new);
    }

}
