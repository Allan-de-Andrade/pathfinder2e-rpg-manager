package com.allan.rpg_manager.application.port.in;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import com.allan.rpg_manager.application.dtos.UserRequest;
import com.allan.rpg_manager.domains.securityDomain.UserDomain;

public interface UserUseCase {
    public UserDomain register(UserRequest userRequest);
    public UserDomain updateUser(UserRequest userRequest,UUID userId,UUID authenticatedUserId);
    public void deleteUser(UUID userId,UUID authenticatedUserId) throws AccessDeniedException;
    public Optional<UserDomain> findById(UUID id);
}
