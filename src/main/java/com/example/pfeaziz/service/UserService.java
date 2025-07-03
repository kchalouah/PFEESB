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
    private User_RoleRepository userRoleRepository;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void deleteUser(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
