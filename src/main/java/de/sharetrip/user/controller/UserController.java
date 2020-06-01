package de.sharetrip.user.controller;

import de.sharetrip.core.exception.AccountLockedException;
import de.sharetrip.core.exception.ResourceAlreadyExistsException;
import de.sharetrip.core.security.user.CurrentUser;
import de.sharetrip.core.security.user.CustomUserDetails;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.dto.UserDto;
import de.sharetrip.user.mapper.UserMapper;
import de.sharetrip.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping(value = "/api/user-management/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping
    public UserDto getUser(@CurrentUser final CustomUserDetails customUserDetails)
            throws AccountLockedException {

        final User user = userService.findUserByUserName(customUserDetails.getUsername());

        return userMapper.map(user);
    }

    @PostMapping
    public UserDto createUser(@Valid @NotEmpty @RequestBody final String firebaseToken)
            throws ResourceAlreadyExistsException, AccountLockedException {

        final User user = userService.createUserFromFirebaseIdToken(firebaseToken);
        return userMapper.map(user);
    }

}
