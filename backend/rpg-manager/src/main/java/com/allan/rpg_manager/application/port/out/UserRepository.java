package com.allan.rpg_manager.application.port.out;

import java.util.Optional;

import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface UserRepository{
    UserDomain save(UserDomain userDomain);
    public Optional<UserDomain> findByUsername(String username);
}
