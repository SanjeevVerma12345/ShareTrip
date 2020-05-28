package de.sharetrip.user.service;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.user.domain.User;

public interface UserService {

    User findUserByUserName(String emailId) throws AccountLockedException;
}
