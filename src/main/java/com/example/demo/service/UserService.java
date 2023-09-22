package com.example.demo.service;

import com.example.demo.config.jwt.JwtService;
import com.example.demo.dto.auth.AuthDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;


    public ResponseEntity<?> checkAuth(AuthDto authDto) {
        UserEntity userEntity = userRepository.findByUsername(authDto.getUsername());
        if (userEntity == null) {
            throw new CustomException.BadRequestException("Username not found");
        }
        if (!(passwordEncoder.matches(authDto.getPassword(), userEntity.getPassword()))) {
            throw new CustomException.BadRequestException("Password not correct");
        }
        String accessToken = jwtService.generatorAccessToken(userEntity);
        String refreshToken = jwtService.generatorRefreshToken(userEntity);
        Map<String, String> mapToken = new HashMap<>();
        mapToken.put("AccessToken", accessToken);
        mapToken.put("RefreshToken", refreshToken);
        return new ResponseEntity<>(mapToken, HttpStatus.OK);

    }




}
