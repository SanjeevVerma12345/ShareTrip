package de.sharetrip.login.controller;

import de.sharetrip.core.secutiry.CurrentUser;
import de.sharetrip.core.secutiry.UserPrincipal;
import de.sharetrip.user.domain.User;
import de.sharetrip.user.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public de.sharetrip.login.controller.User getCurrentUser(@CurrentUser final UserPrincipal userPrincipal) {
        final Optional<User> byId = userRepository.findById(userPrincipal.getId());
        final de.sharetrip.login.controller.User u = new de.sharetrip.login.controller.User();
        byId.ifPresent(dbUser -> BeanUtils.copyProperties(u, dbUser));
        return u;
    }
}
