package de.sharetrip.user.service;

import de.sharetrip.core.domain.Image;
import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.RequestForbiddenException;
import de.sharetrip.core.exception.ResourceAlreadyExistsException;
import de.sharetrip.core.exception.ResourceNotFoundException;
import de.sharetrip.firebase.domain.AuthenticationProvider;
import de.sharetrip.firebase.domain.FirebaseUser;
import de.sharetrip.firebase.utility.FirebaseService;
import de.sharetrip.user.dataprovider.UserDataProvider;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.domain.UserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDataProvider userDataProvider;

    @Override
    @Transactional(readOnly = true)
    public User findUserByUserName(final String userName)
            throws AccountLockedException {
        return userDataProvider.findUserByUserName(userName);
    }

    @Override
    @Transactional
    public User createUserFromFirebaseIdToken(final String idToken)
            throws AccountLockedException, ResourceAlreadyExistsException {

        final FirebaseUser firebaseUser = FirebaseService.resolveFirebaseToken(idToken);

        validateFirebaseTokenForSignUp(firebaseUser);

        final User user = prepareUser(firebaseUser);
        return userDataProvider.saveUser(user);
    }

    private void validateFirebaseTokenForSignUp(final FirebaseUser firebaseUser)
            throws AccountLockedException, ResourceAlreadyExistsException {

        final User user;
        final String firebaseUserEmail = firebaseUser.getEmail();

        try {
            user = findUserByUserName(firebaseUserEmail);
        } catch (final ResourceNotFoundException e) {
            log.debug("User with user name [%s] does not exists.");
            return;
        }

        final AuthenticationProvider userAuthenticationProvider = user.getAuthenticationProvider();

        if (userAuthenticationProvider.equals(firebaseUser.getAuthenticationProvider())) {
            final ResourceAlreadyExistsException e = new ResourceAlreadyExistsException(
                    String.format("User with user name [%s] already exists", firebaseUserEmail));
            log.debug(e.getMessage(), e);
            throw e;
        } else {
            final RequestForbiddenException e = new RequestForbiddenException(
                    String.format("Email id already registered using %s", userAuthenticationProvider.name().toLowerCase()));
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private User prepareUser(final FirebaseUser firebaseUser) {

        final UserDetails userDetails = prepareUserDetails(firebaseUser);

        return User.builder()
                   .username(firebaseUser.getEmail())
                   .enabled(firebaseUser.isEmailVerified())
                   .authenticationProvider(firebaseUser.getAuthenticationProvider())
                   .userDetails(userDetails)
                   .build();
    }

    private UserDetails prepareUserDetails(final FirebaseUser firebaseUser) {
        return UserDetails.builder()
                          .firstName(firebaseUser.getFirstName())
                          .lastName(firebaseUser.getLastName())
                          .userImage(Image.builder()
                                          .url(firebaseUser.getPicture())
                                          .build())
                          .build();
    }

}
