package com.example.pfeaziz.controller;


import com.example.pfeaziz.model.User_Role;
import com.example.pfeaziz.service.User_RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class User_RoleController {

    @Autowired
    private User_RoleService roleService;

    @GetMapping
    public List<User_Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/{id}")
    public User_Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    @PostMapping
    public User_Role createRole(@RequestBody User_Role role) {
        return roleService.saveRole(role);
    }

    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
    }
}
