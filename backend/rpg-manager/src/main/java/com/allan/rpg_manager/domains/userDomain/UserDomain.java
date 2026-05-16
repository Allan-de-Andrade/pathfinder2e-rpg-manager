package com.allan.rpg_manager.domains.userDomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


@Getter
@NoArgsConstructor
public class UserDomain {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private String providerSubject;
    private Set<AuthProviders> providers;
    private boolean isActive;
    
    private static final Pattern email_pattern = Pattern.compile
    ("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    
    public UserDomain(String username,String email, String password) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setIsActive(true);
    }

    public static UserDomain googleUser(String username,String email, String providerSubject){
        UserDomain user = new UserDomain();
        user.setUsername(username);
        user.setEmail(email);
        user.setProvider(providerSubject, AuthProviders.Google);
        user.setIsActive(true);
        return user;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public void setUsername(String username){
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        this.username = username;
    }

    public void setEmail(String email){
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        if (!email_pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid");
        }

        this.email = email;
    }
    
    public void setPassword(String password){
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }        
        
        if(password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }

        this.password = password;
    }
    
    public void setProvider(String providerSubject, AuthProviders provider){
        if (provider == null) {
            throw new IllegalArgumentException("Provider cannot be null");
        }
        this.providerSubject = providerSubject;
        this.providers = Set.of(provider);
    }

    public boolean getIsActive(){
        return this.isActive;
    }

    public boolean setIsActive(boolean isActive){
        this.isActive = isActive;
        return this.isActive;
    }
}
