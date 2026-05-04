package com.allan.rpg_manager.infrastructure.adapter.in;

import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.allan.rpg_manager.application.port.out.UserRepository;
import com.allan.rpg_manager.domains.userDomain.UserDomain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SecurityAdapter implements UserDetailsService {

    private final UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDomain> userOptional  = userRepository.findByUsername(username);
        
        if (userOptional.isPresent()) {
            UserDomain user = userOptional.get();
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
    
}
