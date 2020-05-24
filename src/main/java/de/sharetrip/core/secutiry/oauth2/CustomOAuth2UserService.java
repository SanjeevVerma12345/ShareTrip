package de.sharetrip.core.secutiry.oauth2;

import de.sharetrip.core.exception.OAuth2AuthenticationProcessingException;
import de.sharetrip.core.secutiry.UserPrincipal;
import de.sharetrip.core.secutiry.oauth2.user.OAuth2UserInfo;
import de.sharetrip.core.secutiry.oauth2.user.OAuth2UserInfoFactory;
import de.sharetrip.user.domain.AuthProvider;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.domain.UserDetails;
import de.sharetrip.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private static final String EMAIL_NOT_FOUND_ERROR_MESSAGE = "Email not found";

    @Override
    public OAuth2User loadUser(final OAuth2UserRequest oAuth2UserRequest) {

        final OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (final AuthenticationException ex) {
            throw ex;
        } catch (final Exception ex) {
            log.error("Exception while loading user", ex);
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(final OAuth2UserRequest oAuth2UserRequest,
                                         final OAuth2User oAuth2User) {
        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        final AuthProvider provider = AuthProvider.findProvider(registrationId);

        final OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider.name(),
                oAuth2User.getAttributes());

        if (StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException(EMAIL_NOT_FOUND_ERROR_MESSAGE);
        }

        final Optional<User> userOptional = userRepository.findByEmailId(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if (!user.getProvider().equals(provider)) {

                final String message = String.format("Please login using your %s account", user.getProvider());
                throw new OAuth2AuthenticationProcessingException(message);
            }
            user = updateExistingUser(user, oAuth2UserInfo);
        } else {
            user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private User registerNewUser(final OAuth2UserRequest oAuth2UserRequest,
                                 final OAuth2UserInfo oAuth2UserInfo) {
        final User user = new User();
        final String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
        final AuthProvider provider = AuthProvider.findProvider(registrationId);

        user.setProvider(provider);
        user.setProviderId(oAuth2UserInfo.getId());
        user.setEmailId(oAuth2UserInfo.getEmail());
        user.setUserName(oAuth2UserInfo.getEmail());
        //        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        final UserDetails userDetails = new UserDetails();
        userDetails.setUser(user);
        user.setUserDetails(userDetails);
        return userRepository.save(user);
    }

    private User updateExistingUser(final User existingUser,
                                    final OAuth2UserInfo oAuth2UserInfo) {
        //        existingUser.setName(oAuth2UserInfo.getName());
        //        existingUser.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(existingUser);
    }

}
