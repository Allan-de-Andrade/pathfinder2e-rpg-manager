package com.allan.rpg_manager.infrastructure.adapter.inbound.web;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/characters")
public class CharacterController{
    @GetMapping("/")
    public void findAll(){
        
    }
}
