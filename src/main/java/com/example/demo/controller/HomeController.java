package com.example.demo.controller;

import com.example.demo.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.auth.SignUpAuthDto;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserService userService;

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
    public String getSignUp(Model model){
        model.addAttribute("user", new SignUpAuthDto());
        return "register";
    }
    @PostMapping("/register")
    public ModelAndView postSignUp(@ModelAttribute("user") @Valid SignUpAuthDto dto, BindingResult bindingResult, HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        if (bindingResult.hasErrors()) {
            return mav;
        }
        try {
            userService.registerAuth(dto);
            mav.addObject("message", "User registration successful.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            mav.addObject("message", e.getMessage());
        }
        return mav;
        
    }
    @GetMapping("/dashboard")
    public String getDashboard(){
        return "dashboard";
    }


}
