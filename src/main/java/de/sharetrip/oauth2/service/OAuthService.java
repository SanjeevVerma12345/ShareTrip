package de.sharetrip.oauth2.service;

import com.google.firebase.auth.FirebaseToken;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.core.security.TokenProvider;
import de.sharetrip.core.security.user.CustomUserDetails;
import de.sharetrip.oauth2.dto.AuthorizeDto;
import de.sharetrip.oauth2.dto.OAuth2Response;
import de.sharetrip.oauth2.utility.FirebaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class OAuthService {

    private final TokenProvider tokenProvider;

    public OAuth2Response prepareOAuthResponse(final AuthorizeDto authorizeDto)
            throws UserNotAuthorizedException {

        final String emailId = authorizeDto.getEmailId();
        final String idToken = authorizeDto.getIdToken();
        final FirebaseToken firebaseToken = FirebaseService.getFirebaseToken(idToken, emailId);

        return new OAuth2Response(
                prepareJsonWebToken(firebaseToken),
                tokenProvider.getTokenExpiration());
    }

    private String prepareJsonWebToken(final FirebaseToken firebaseToken) {
        final CustomUserDetails customUserDetails = new CustomUserDetails(firebaseToken.getEmail());
        return tokenProvider.createToken(customUserDetails);
    }

}
