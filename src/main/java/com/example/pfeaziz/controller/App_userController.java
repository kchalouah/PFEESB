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
import java.util.Collections;

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

    @GetMapping
    public List<App_user> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<App_user> getUserById(@PathVariable Long id) {
        Optional<App_user> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<App_user> updateUser(@PathVariable Long id, @RequestBody App_user user) {
        if (!userService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        App_user updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser);
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

            // Always fetch roles from DB to avoid detached entities
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                List<User_Role> attachedRoles = user.getRoles().stream()
                        .map(role -> roleService.getRoleById(role.getId()))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList());
                user.setRoles(attachedRoles);
            } else {
                // Assign default ROLE_USER if no roles are provided
                User_Role defaultRole = roleService.getRoleByName("ROLE_USER");
                if (defaultRole == null) {
                    defaultRole = new User_Role();
                    defaultRole.setRoleName("ROLE_USER");
                    defaultRole = roleService.saveRole(defaultRole);
                }
                user.setRoles(Collections.singletonList(defaultRole));
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
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getAuthority())
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
