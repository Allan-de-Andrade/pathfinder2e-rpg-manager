package com.allan.rpg_manager.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.allan.rpg_manager.domains.securityDomain.UserDomain;

public interface UserRepository{
    UserDomain save(UserDomain userDomain);
    Optional<UserDomain> findByEmail(String email);
    Optional<UserDomain> findByProviderSubject(String providerSubject);
    UserDomain updateUser(UserDomain userDomain);
    Optional<UserDomain> findById(UUID id);
    void deleteById(UUID id);
}
