package com.example.pfeaziz.controller;


import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.User_Role;
import com.example.pfeaziz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public Optional<App_user> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

    @GetMapping("/roles")
    public List<User_Role> getAllRoles() {
        return userService.getAllRoles();
    }
}
