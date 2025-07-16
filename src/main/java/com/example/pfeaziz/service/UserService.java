package com.example.pfeaziz.service;


import com.example.pfeaziz.model.App_user;
import com.example.pfeaziz.model.User_Role;
import com.example.pfeaziz.repository.App_userRepository;
import com.example.pfeaziz.repository.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private App_userRepository appUserRepository;

    @Autowired
    private User_RoleRepository userRoleRepository; // Repository for User_Role

    public Optional<App_user> getUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    public List<User_Role> getAllRoles() {
        return userRoleRepository.findAll();
    }

    public Object findById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean existsById(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void saveUser(App_user user) {
        appUserRepository.save(user);
    }

    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public App_user registerNewUser(App_user user) {
        // ...existing code to set username/password...

        // Assign default role if none set
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            User_Role defaultRole = userRoleRepository.findByRoleName("ROLE_USER");
            if (defaultRole == null) {
                defaultRole = new User_Role();
                defaultRole.setRoleName("ROLE_USER");
                defaultRole = userRoleRepository.save(defaultRole);
            }
            user.setRoles(List.of(defaultRole));
        } else {
            // Ensure roles are managed entities
            List<User_Role> managedRoles = user.getRoles().stream()
                .map(role -> role.getId() != null ? userRoleRepository.findById(role.getId()).orElse(null) : null)
                .filter(r -> r != null)
                .toList();
            user.setRoles(managedRoles);
        }

        return appUserRepository.save(user);
    }

    public List<App_user> findAll() {
        return appUserRepository.findAll();
    }

    public List<App_user> getAllUsers() {
        return appUserRepository.findAll();
    }
}
