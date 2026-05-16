package com.allan.rpg_manager.application.port.out;

import java.util.Optional;
import java.util.UUID;

import com.allan.rpg_manager.domains.userDomain.UserDomain;

public interface UserRepository{
    public UserDomain save(UserDomain userDomain);
    public Optional<UserDomain> findByEmail(String email);
    public Optional<UserDomain> findByProviderSubject(String providerSubject);
    public UserDomain updateUser(UserDomain userDomain);
    public Optional<UserDomain> findById(UUID id);
    public void deleteById(UUID id);
}
