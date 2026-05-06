package com.allan.rpg_manager.application.port.in;
import java.util.Optional;
import java.util.UUID;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface UserUseCase {
    public UserDomain register(Optional<UserDomain> userRequest);
    public LoginResponse login(Optional<UserDomain> userRequest);
    public UserDomain updateUser(Optional<UserDomain> userRequest,UUID userId);
    public Boolean deleteUser(UUID userId);
}