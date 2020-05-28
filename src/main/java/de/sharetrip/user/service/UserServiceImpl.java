package de.sharetrip.user.service;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.user.dataprovider.UserDataProvider;
import de.sharetrip.user.domain.User;
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

        final User user = userDataProvider.findUserByUserName(userName);

        if (user.isAccountNonLocked()) {
            return user;
        } else {
            log.error("User account with user name [%s] is locked.", user.getUsername());
            throw new AccountLockedException();
        }
    }
}
