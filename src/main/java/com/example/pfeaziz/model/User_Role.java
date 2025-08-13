package com.example.pfeaziz.model;

import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User_Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    private String description; // Added to match frontend

    public User_Role() {}

    public User_Role(Long id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return roleName;
    }

    @JsonProperty("authority")
    public String getAuthorityForJson() {
        return roleName;
    }
}
