package de.sharetrip.core.secutiry.oauth2.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(final Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) super.getAttributes().get("sub");
    }

    @Override
    public String getName() {
        return (String) super.getAttributes().get("name");
    }

    @Override
    public String getEmail() {
        return (String) super.getAttributes().get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) super.getAttributes().get("picture");
    }
}
