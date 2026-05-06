package com.allan.rpg_manager.application.dtos;

import com.allan.rpg_manager.domains.userDomain.UserDomain;

public record UserRequest(String username,String email, String password) {
    public UserRequest(UserDomain userDomain) {
        this(userDomain.getUsername(), userDomain.getEmail(), userDomain.getPassword());
    }
} 
