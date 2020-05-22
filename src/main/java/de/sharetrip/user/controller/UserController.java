package de.sharetrip.user.controller;

import de.sharetrip.user.domain.User;
import de.sharetrip.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user-management/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> findAllUsers() {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            System.out.println(user);
        }
        return null;
    }

}
