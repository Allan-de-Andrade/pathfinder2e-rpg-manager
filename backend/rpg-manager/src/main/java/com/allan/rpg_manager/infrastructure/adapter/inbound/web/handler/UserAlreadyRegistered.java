package com.allan.rpg_manager.infrastructure.adapter.inbound.web.handler;

public class UserAlreadyRegistered extends RuntimeException {
    public UserAlreadyRegistered(String message) {
        super(message);
    }
}
