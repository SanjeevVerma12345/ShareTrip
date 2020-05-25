package de.sharetrip.core.util;

import de.sharetrip.core.exception.BadRequestException;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class RequestHeaderUtility {

    private static final String BEARER_TYPE = "Bearer ";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_USER = "user";

    public static String getJwtFromRequest(final HttpServletRequest request) {

        final String bearerToken = request.getHeader(HEADER_AUTHORIZATION);

        if (StringUtils.hasText(bearerToken) &&
                bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }

        throw new BadRequestException();
    }

    public static String getUserFromRequest(final HttpServletRequest request) {

        final String bearerToken = request.getHeader(HEADER_USER);

        if (StringUtils.isEmpty(bearerToken)) {
            throw new BadRequestException();
        }

        return bearerToken;
    }

}
