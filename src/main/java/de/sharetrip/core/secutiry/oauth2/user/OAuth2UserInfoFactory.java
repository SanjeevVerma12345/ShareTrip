package de.sharetrip.core.secutiry.oauth2.user;

import de.sharetrip.core.exception.OAuth2AuthenticationProcessingException;
import de.sharetrip.user.domain.AuthProvider;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class OAuth2UserInfoFactory {

    private static final String ERROR_MESSAGE = "FUCK OFF USER";

    public static OAuth2UserInfo getOAuth2UserInfo(final String registrationId, final Map<String, Object> attributes) {
        //        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
        //            return new GoogleOAuth2UserInfo(attributes);
        //        } else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
        //            return new FacebookOAuth2UserInfo(attributes);
        //        } else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
        //            return new GithubOAuth2UserInfo(attributes);
        //        } else {
        //            throw new OAuth2AuthenticationProcessingException("Sorry! Login with " + registrationId + " is not supported yet.");
        //        }

        if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
            return new FacebookOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(ERROR_MESSAGE);
        }
    }
}
