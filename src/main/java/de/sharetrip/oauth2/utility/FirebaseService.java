package de.sharetrip.oauth2.utility;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import de.sharetrip.core.exception.BadRequestException;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.oauth2.domain.FirebaseUser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@UtilityClass
public class FirebaseService {

    public static FirebaseUser getVerifiedFirebaseToken(final String idToken,
                                                        final String emailId) throws UserNotAuthorizedException {

        final FirebaseUser firebaseToken = FirebaseService.resolveFirebaseToken(idToken);

        //TODO: what if firebase email is not verified ?
        return Optional.of(firebaseToken)
                       .filter(token -> token.getEmail().equals(emailId))
                       .orElseThrow(UserNotAuthorizedException::new);
    }

    public static FirebaseUser resolveFirebaseToken(final String idToken) {
        try {
            final FirebaseToken firebaseToken = FirebaseAuth
                    .getInstance()
                    .verifyIdToken(idToken);

            return new FirebaseUser(firebaseToken);
        } catch (final FirebaseAuthException e) {
            log.error(String.format("Could not resolve firebase token", idToken), e);
            throw new BadRequestException();
        }
    }
}
