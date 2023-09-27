package com.example.demo.api;

import com.example.demo.config.jwt.JwtFilter;
import com.example.demo.config.jwt.JwtService;
import com.example.demo.dto.auth.ChangeAuthDto;
import com.example.demo.dto.auth.SigninAuthDto;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthApiController {
    private final UserService userService;
    private final JwtFilter jwtFilter;
    private final JwtService jwtService;
    @Autowired
    public AuthApiController(UserService userService, JwtFilter jwtFilter, JwtService jwtService) {
        this.userService = userService;
        this.jwtFilter = jwtFilter;
        this.jwtService = jwtService;
    }



    @PostMapping("/authenticated")
    public ResponseEntity<?> authenticated(HttpServletRequest request){
        String accessToken = request.getHeader("Authorization");
        if(jwtFilter.hasAuthorizationBearer(accessToken)){
            String token = accessToken.split(" ")[1].trim();
            if(jwtService.validateAccessToken(token)){
                return new ResponseEntity<>("Token is valid", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Token is invalid", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody SigninAuthDto dto){
        return new ResponseEntity<>(userService.loginAuth(dto).getMap(), HttpStatus.OK);
    }

    @PostMapping("/change")
    public ResponseEntity<?> login(@Validated @RequestBody ChangeAuthDto dto){
        return new ResponseEntity<>(userService.changeAuth(dto).getMap(), HttpStatus.OK);
    }

}
