package de.sharetrip.oauth2.utility;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@UtilityClass
public class FirebaseService {

    public static FirebaseToken getFirebaseToken(final String idToken,
                                                 final String emailId) throws UserNotAuthorizedException {
        final FirebaseToken firebaseToken;
        try {
            firebaseToken = FirebaseAuth
                    .getInstance()
                    .verifyIdToken(idToken);
        } catch (final FirebaseAuthException e) {
            log.error(String.format("Could not authenticate firebase id token for id [%s]", idToken), e);
            throw new UserNotAuthorizedException();
        }
        return Optional.of(firebaseToken)
                       .filter(token -> token.getEmail().equals(emailId))
                       .orElseThrow(UserNotAuthorizedException::new);
    }
}
