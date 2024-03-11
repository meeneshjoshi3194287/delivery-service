package com.nagp.userservice.entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonAutoDetect(getterVisibility= JsonAutoDetect.Visibility.NONE)
public class UserEntity {

    @Id
    private String username;

    private String password;


    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "username"))
    @Column(name = "role")
    private Set<String> roles;

    public UserEntity() {
        this.roles = new HashSet<>();
    }

    public UserEntity(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles = new HashSet<>();
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();
        builder.append("User{username=").append(username)
                .append(", password=").append(password)
                .append(", roles=").append(roles)
                .append("}");
        return builder.toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
