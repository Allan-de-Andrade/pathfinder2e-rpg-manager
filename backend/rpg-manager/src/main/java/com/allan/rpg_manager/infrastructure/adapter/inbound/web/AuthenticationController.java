package com.allan.rpg_manager.infrastructure.adapter.inbound.web;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.allan.rpg_manager.application.dtos.GoogleLoginRequest;
import com.allan.rpg_manager.application.dtos.LoginRequest;
import com.allan.rpg_manager.application.dtos.LoginResponse;
import com.allan.rpg_manager.application.port.in.AuthenticationUseCase;
import com.allan.rpg_manager.application.port.in.TokenUseCase;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;
    private final TokenUseCase tokenUseCase;
    
    public AuthenticationController(AuthenticationUseCase authenticationUseCase,TokenUseCase tokenUseCase) {
        this.authenticationUseCase = authenticationUseCase;
        this.tokenUseCase = tokenUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authenticationUseCase.loginWithCredentials(loginRequest);
        ResponseCookie cookie  = createRefreshTokenCookie(response.refresh_token(), response.refresh_expirationTime());
            
        return ResponseEntity.ok().
        header(HttpHeaders.CACHE_CONTROL,"no-store").
        contentType(MediaType.APPLICATION_JSON).
        header(HttpHeaders.SET_COOKIE,cookie.toString()).
        body(Map.of("access_token", response.access_token()));
    }
  
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@CookieValue("refresh_token") String refreshToken) {
        authenticationUseCase.logout(refreshToken);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String,String>> refreshAcessToken(@CookieValue("refresh_token") String refreshToken){
        LoginResponse response = tokenUseCase.refreshAcessToken(refreshToken);
        ResponseCookie cookie  = createRefreshTokenCookie(response.refresh_token(), response.refresh_expirationTime());
            
        return ResponseEntity.ok().
        header(HttpHeaders.CACHE_CONTROL,"no-store").
        contentType(MediaType.APPLICATION_JSON).
        header(HttpHeaders.SET_COOKIE,cookie.toString()).
        body(Map.of("access_token", response.access_token()));
    }

    @PostMapping("/google-login")
    public ResponseEntity<Map<String,String>> googleLogin(@RequestBody GoogleLoginRequest request) {
        LoginResponse response = authenticationUseCase.loginWithGoogle(request.idToken());
        ResponseCookie cookie  = createRefreshTokenCookie(response.refresh_token(), response.refresh_expirationTime());
            
        return ResponseEntity.ok().
        header(HttpHeaders.CACHE_CONTROL,"no-store").
        contentType(MediaType.APPLICATION_JSON).
        header(HttpHeaders.SET_COOKIE,cookie.toString()).
        body(Map.of("access_token", response.access_token()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken, Long expirationTime) {
        return ResponseCookie.from("refresh_token", refreshToken)
            .httpOnly(true)
            .secure(true)
            .path("/api/auth/")
            .maxAge(expirationTime) 
            .sameSite("Strict")
            .build();
    }
}
