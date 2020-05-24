package de.sharetrip.core.secutiry.oauth2.user;

import de.sharetrip.core.exception.OAuth2AuthenticationProcessingException;
import de.sharetrip.user.domain.AuthProvider;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class OAuth2UserInfoFactory {

    private static final String ERROR_MESSAGE = "Not supported";

    public static OAuth2UserInfo getOAuth2UserInfo(final String registrationId, final Map<String, Object> attributes) {
        if (registrationId.equals(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equals(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(ERROR_MESSAGE);
        }

    }
}
