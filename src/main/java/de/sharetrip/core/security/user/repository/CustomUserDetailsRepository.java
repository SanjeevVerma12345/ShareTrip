package de.sharetrip.core.security.user.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.sharetrip.core.cache.CacheManager;
import de.sharetrip.core.exception.UserNotAuthorizedException;
import de.sharetrip.core.security.user.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomUserDetailsRepository {

    private static final String USER_CACHE_NAME = "users";

    private final CacheManager<CustomUserDetails> cacheManager;

    public CustomUserDetails getUserDetailsByUserName(final String userName)
            throws UserNotAuthorizedException {

        try {
            return cacheManager.getValueFromCache(
                    USER_CACHE_NAME,
                    userName,
                    CustomUserDetails.class);
        } catch (final JsonProcessingException e) {
            log.error("Could not get user details", e);
            throw new UserNotAuthorizedException();
        }

    }
}
