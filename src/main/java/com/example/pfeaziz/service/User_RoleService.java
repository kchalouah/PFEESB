
package com.example.pfeaziz.service;


import com.example.pfeaziz.model.User_Role;
import com.example.pfeaziz.repository.User_RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class User_RoleService {

    @Autowired
    private User_RoleRepository roleRepository;

    public List<User_Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public User_Role getRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    public User_Role saveRole(User_Role role) {
        return roleRepository.save(role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}