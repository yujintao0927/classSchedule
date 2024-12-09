package org.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/student")
    public String studentPage() {
        return "student";
    }

    @GetMapping("/classroom")
    public String classroom() {
        return "classroom";
    }

    @GetMapping("/teacher")
    public String teacher() {
        return "teacher";
    }
} 