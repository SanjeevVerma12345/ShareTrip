package de.sharetrip.core.secutiry.oauth2.user;

import java.util.Map;

public class FacebookOAuth2UserInfo extends OAuth2UserInfo {

    private static final String PICTURE = "picture";
    private static final String DATA = "data";
    private static final String URL = "url";
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String EMAIL = "name";

    public FacebookOAuth2UserInfo(final Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) super.getAttributes().get(ID);
    }

    @Override
    public String getName() {
        return (String) super.getAttributes().get(NAME);
    }

    @Override
    public String getEmail() {
        return (String) super.getAttributes().get(EMAIL);
    }

    @Override
    public String getImageUrl() {

        if (super.getAttributes().containsKey(PICTURE)) {

            final Map<String, Object> pictureObj = (Map<String, Object>) super.getAttributes().get(PICTURE);
            if (pictureObj.containsKey(DATA)) {
                final Map<String, Object> dataObj = (Map<String, Object>) pictureObj.get(DATA);
                if (dataObj.containsKey(URL)) {
                    return (String) dataObj.get(URL);
                }
            }
        }
        return null;
    }
}
