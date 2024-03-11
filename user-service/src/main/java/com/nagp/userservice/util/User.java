package com.nagp.userservice.util;

import com.nagp.userservice.entity.UserEntity;

import java.util.Set;

public class User {

    private String username;
    private Set<String> roles;

    public User(UserEntity entity) {
        this.username = entity.getUsername();
        this.roles = entity.getRoles();
        roles.add("ROLE_USER");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}