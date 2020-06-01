package de.sharetrip.user.dataprovider;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.ResourceNotFoundException;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class UserDataProvider {

    private final UserRepository userRepository;

    @Cacheable(value = "users", key = "#userName")
    public User findUserByUserName(final String userName) throws AccountLockedException {
        log.info(String.format("Fetching user with user name [%s]", userName));
        final User user = userRepository.findByUsername(userName)
                                        .orElseThrow(() -> new ResourceNotFoundException(
                                                String.format("User with user name [%s] not found.", userName)));
        return Optional
                .of(user)
                .filter(User::isAccountNonLocked)
                .orElseThrow(() -> new AccountLockedException(
                        String.format("User account with user name [%s] is locked.", userName)));
    }

    @Cacheable(value = "users", key = "#user.username")
    public User saveUser(final User user) {
        log.info(String.format("Creating user [%s]", user));
        return userRepository.save(user);
    }
}
