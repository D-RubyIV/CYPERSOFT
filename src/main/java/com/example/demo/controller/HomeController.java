package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    @GetMapping("/")
    public String home(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Lấy tên người dùng đã đăng nhập
        model.addAttribute("username", username);
        System.out.println(username);
        return "home";
    }
    @GetMapping("/base")
    public String base(){
        return "base";
    }
    @GetMapping("/login")
    public String getSignIn(){
        return "login";
    }
    @GetMapping("/register")
    public String getSignUp(){
        return "register";
    }
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }


}
