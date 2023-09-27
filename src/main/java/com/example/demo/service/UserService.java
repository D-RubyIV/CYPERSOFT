package com.example.demo.service;

import com.example.demo.config.jwt.JwtService;
import com.example.demo.dto.auth.BearerTokenDto;
import com.example.demo.dto.auth.SigninAuthDto;
import com.example.demo.dto.auth.ChangeAuthDto;
import com.example.demo.dto.auth.SignUpAuthDto;
import com.example.demo.dto.response.MapResponseDto;
import com.example.demo.exception.CustomException;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public void registerAuth(SignUpAuthDto authDto){
        if (!authDto.getPassword().equals(authDto.getRePassword())) {
            throw new CustomException.BadRequestException("Mật khẩu và xác nhận mật khẩu phải giống nhau");
        }
        if (userRepository.findByEmail(authDto.getEmail()) != null){
            throw new CustomException.BadRequestException("Email is taken by other account");
        }
        if (userRepository.findByUsername(authDto.getUsername()) != null){
            throw new CustomException.BadRequestException("Username is taken by other account");
        }
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(authDto, userEntity);
        userEntity.setPassword(passwordEncoder.encode(authDto.getPassword()));
        userEntity.setEnable(true);
        userEntity.setListRole(List.of(roleRepository.findByCode("ROLE_USER")));
        userRepository.save(userEntity);
    }

    public MapResponseDto loginAuth(SigninAuthDto authDto) {
        UserEntity userEntity = userRepository.findByUsername(authDto.getUsername());

        if (userEntity == null) {
            throw new CustomException.BadRequestException("Username not found");
        }
        if (!(passwordEncoder.matches(authDto.getPassword(), userEntity.getPassword()))) {
            throw new CustomException.BadRequestException("Password not correct");
        }
        String accessToken = jwtService.generatorAccessToken(userEntity);
        String refreshToken = jwtService.generatorRefreshToken(userEntity);

        Map<String, String> map = new HashMap<>();
        map.put("AccessToken", accessToken);
        map.put("RefreshToken", refreshToken);
        return new MapResponseDto(map);
    }

    public MapResponseDto changeAuth(ChangeAuthDto changePassAuthDto){
        UserEntity userEntity = userRepository.findByUsername(changePassAuthDto.getUsername());
        if(userEntity == null){
            throw new CustomException.BadRequestException("Username not found");
        }
        if (!(passwordEncoder.matches(changePassAuthDto.getPassword(), userEntity.getPassword()))) {
            throw new CustomException.BadRequestException("Password not correct");
        }
        userEntity.setPassword(passwordEncoder.encode(changePassAuthDto.getNewPassword()));
        userRepository.save(userEntity);
        
        Map<String, String> map = new HashMap<>();
        map.put("message", "Password has been changed");
        return new MapResponseDto(map);
    }



}
