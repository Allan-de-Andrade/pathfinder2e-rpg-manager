package com.allan.rpg_manager.domains.userDomain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDomain {
    public String username;
    public String email;
    public String password;
}
