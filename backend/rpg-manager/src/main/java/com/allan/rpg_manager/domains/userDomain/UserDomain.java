package com.allan.rpg_manager.domains.userDomain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.regex.Pattern;

import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class UserDomain {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private boolean isActive = true;
    
    private static final Pattern email_pattern = Pattern.compile
    ("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    
    public UserDomain(String username,String email, String password, boolean isActive) {
        setUsername(username);
        setEmail(email);
        setPassword(password);
        setIsActive(isActive);
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

    public boolean isLoginCorrect(String email, String password,PasswordEncoder encoder) {
        return this.email.equals(email) && encoder.matches(password, this.password);
    }
    public boolean getIsActive(){
        return this.isActive;
    }
    public boolean setIsActive(boolean isActive){
        this.isActive = isActive;
        return this.isActive;
    }
}
