package com.example.pfeaziz.controller;


import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.UserDTO;
import com.example.pfeaziz.model.User_Role;
import com.example.pfeaziz.security.JwtTokenProvider;
import com.example.pfeaziz.service.App_userService;
import com.example.pfeaziz.service.User_RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class App_userController {

    private final App_userService userService;
    private final User_RoleService roleService;
    private final JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(App_userController.class);

    public App_userController(App_userService userService, User_RoleService roleService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.roleService = roleService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody App_user user) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (user.getUsername() == null || user.getUsername().isBlank()) {
                response.put("message", "Username is required.");
                return ResponseEntity.badRequest().body(response);
            }
            if (user.getPassword() == null || user.getPassword().isBlank()) {
                response.put("message", "Password is required.");
                return ResponseEntity.badRequest().body(response);
            }

            if (userService.findByUsername(user.getUsername()).isPresent()) {
                response.put("message", "User already exists!");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            }

            if (user.getUser_roles() != null && !user.getUser_roles().isEmpty()) {
                List<User_Role> roles = user.getUser_roles().stream()
                        .map(role -> roleService.getRoleById(role.getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                user.setUser_roles(roles);
            }

            App_user savedUser = userService.saveUser(user);

            response.put("message", "User registered successfully!");
            response.put("user", savedUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            logger.error("Error during registration", e);
            response.put("message", "Registration failed.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO userDTO) {
        Map<String, Object> response = new HashMap<>();
        Optional<App_user> optionalUser = userService.findByUsername(userDTO.getUsername());

        if (optionalUser.isEmpty()) {
            response.put("message", "User not found!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        App_user user = optionalUser.get();
        if (!Objects.equals(user.getPassword(), userDTO.getPassword())) {
            response.put("message", "Invalid password!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = jwtTokenProvider.generateToken(user);
        List<String> roles = user.getUser_roles().stream()
                .map(User_Role::getAuthority)
                .collect(Collectors.toList());

        response.put("message", "Login successful!");
        response.put("token", token);
        response.put("roles", roles);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/roles")
    public ResponseEntity<List<User_Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
