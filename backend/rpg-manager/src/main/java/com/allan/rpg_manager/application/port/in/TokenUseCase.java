package com.allan.rpg_manager.application.port.in;

import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface TokenUseCase {
    LoginResponse generateToken(UserDomain userDomain);
}
