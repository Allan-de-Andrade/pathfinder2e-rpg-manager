package com.allan.rpg_manager.application.port.out;

import java.util.UUID;

import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface UserRepository{
    UserDomain save(UserDomain userDomain);
    public UserDomain findByEmail(String email);
    public UserDomain updateUser(UserDomain userRequest);
    public void deleteById(UUID id);
}
