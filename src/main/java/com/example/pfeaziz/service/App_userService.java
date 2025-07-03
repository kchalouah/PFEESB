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
public class App_userService {

    @Autowired
    private App_userRepository userRepository;
    
    @Autowired
    private User_RoleRepository userRoleRepository;
    
    public Optional<App_user> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<App_user> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<App_user> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public App_user saveUser(App_user user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    
    public List<User_Role> getAllRoles() {
        return userRoleRepository.findAll();
    }

    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }
}
