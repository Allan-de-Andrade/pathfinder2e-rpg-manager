package com.allan.rpg_manager.infrastructure.adapter.outbound;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import com.allan.rpg_manager.application.dtos.GoogleUserInfo;
import com.allan.rpg_manager.application.port.out.GoogleAuthPort;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;

@Component
public class GoogleAuthAdapter implements GoogleAuthPort {
    private final GoogleIdTokenVerifier verifier;

    public GoogleAuthAdapter(@Value("${google.client.id}") String googleClientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();
    }

    @Override
    public GoogleUserInfo verifyIdToken(String idTokenValue) {
        try {
            GoogleIdToken token = verifier.verify(idTokenValue);

            if (token == null) {
                throw new BadCredentialsException("Invalid Google token");
            }

            GoogleIdToken.Payload payload = token.getPayload();

            Boolean emailVerified = payload.getEmailVerified();
            if (!Boolean.TRUE.equals(emailVerified)) {
                throw new BadCredentialsException("Google email is not verified");
            }

            return new GoogleUserInfo(
                    payload.getSubject(),
                    payload.getEmail(),
                    (String) payload.get("name"),
                    true);
        } 
        
        catch (Exception e) {
            throw new BadCredentialsException("Invalid Google token", e);
        }
    }

}
