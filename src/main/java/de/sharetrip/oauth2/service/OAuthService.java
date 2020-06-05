package de.sharetrip.oauth2.service;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.core.security.TokenProvider;
import de.sharetrip.core.security.user.CustomUserDetails;
import de.sharetrip.firebase.utility.FirebaseService;
import de.sharetrip.oauth2.dto.AuthorizeDto;
import de.sharetrip.oauth2.dto.OAuth2Response;
import de.sharetrip.user.dataprovider.UserDataProvider;
import de.sharetrip.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OAuthService {

    private final TokenProvider tokenProvider;

    private final UserDataProvider userDataProvider;

    public OAuth2Response prepareOAuthResponse(final AuthorizeDto authorizeDto)
            throws UserNotAuthorizedException, AccountLockedException {

        final String emailId = authorizeDto.getEmailId();
        final String idToken = authorizeDto.getIdToken();

        FirebaseService.getVerifiedFirebaseToken(idToken, emailId);

        final User user = userDataProvider.findUserByUserName(emailId);
        final String jsonWebToken = prepareJsonWebToken(user);
        final int tokenExpiration = tokenProvider.getTokenExpiration();

        return new OAuth2Response(
                jsonWebToken,
                tokenExpiration);
    }

    private String prepareJsonWebToken(final User user) {

        final CustomUserDetails customUserDetails = CustomUserDetails
                .builder()
                .uuid(user.getUuid())
                .username(user.getUsername())
                .accountNonLocked(user.isAccountNonLocked())
                .enabled(user.isEnabled())
                .authenticationProvider(user.getAuthenticationProvider().name())
                .build();

        return tokenProvider.createToken(customUserDetails);
    }
}
