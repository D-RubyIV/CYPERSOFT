package com.example.demo.api;

import com.example.demo.dto.auth.ChangeAuthDto;
import com.example.demo.dto.auth.LoginAuthDto;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginAuthDto dto){
        return userService.loginAuth(dto);
    }

     @PostMapping("/change")
    public ResponseEntity<?> login(@Validated @RequestBody ChangeAuthDto dto){
        return userService.changeAuth(dto);
    }

}
