package de.sharetrip.user.dataprovider;

import de.sharetrip.core.exception.ResourceNotFoundException;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class UserDataProvider {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#userName")
    public User findUserByUserName(final String userName) {
        log.info(String.format("Fetching user with user name [%s]", userName));

        return userRepository
                .findByUsername(userName)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
