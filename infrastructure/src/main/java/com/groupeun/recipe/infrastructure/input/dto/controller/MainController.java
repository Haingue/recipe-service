package com.groupeun.recipe.infrastructure.input.dto.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public ResponseEntity getHome () {
        return ResponseEntity.ok("UP");
    }
    
}
