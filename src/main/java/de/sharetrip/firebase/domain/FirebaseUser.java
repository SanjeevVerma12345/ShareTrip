package de.sharetrip.firebase.domain;

import com.google.api.client.util.ArrayMap;
import com.google.firebase.auth.FirebaseToken;
import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value
public final class FirebaseUser {

    private final FirebaseToken firebaseToken;

    public FirebaseUser(final FirebaseToken firebaseToken) {
        this.firebaseToken = firebaseToken;
    }

    public String getUid() {
        return firebaseToken.getUid();
    }

    public String getFirstName() {
        final String name = firebaseToken.getName();

        return name.contains(StringUtils.SPACE) ?
                name.split(StringUtils.SPACE)[0] :
                name;
    }

    public String getLastName() {
        final String name = firebaseToken.getName();

        return name.contains(StringUtils.SPACE) ?
                name.split(StringUtils.SPACE)[1] :
                null;
    }

    public String getName() {
        return firebaseToken.getName();
    }

    public String getPicture() {
        return firebaseToken.getPicture();
    }

    public String getEmail() {
        return firebaseToken.getEmail();
    }

    public boolean isEmailVerified() {
        return firebaseToken.isEmailVerified();
    }

    public AuthenticationProvider getAuthenticationProvider() {
        final String authenticationProvider = ((ArrayMap)
                firebaseToken.getClaims()
                             .get("firebase"))
                .get("sign_in_provider")
                .toString();

        return AuthenticationProvider.findBySignInProvider(authenticationProvider);
    }
}
