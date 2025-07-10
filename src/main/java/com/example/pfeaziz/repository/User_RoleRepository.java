/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.pfeaziz.repository;


import com.example.pfeaziz.model.User_Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface User_RoleRepository extends JpaRepository<User_Role, Long> {
    User_Role findByRoleName(String roleName);
}
