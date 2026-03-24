package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugController {

    @GetMapping("/debug")
    public String debug(Authentication auth) {
        if (auth == null) {
            return "auth = null";
        }
        return "name=" + auth.getName() + ", authorities=" + auth.getAuthorities();
    }
}
