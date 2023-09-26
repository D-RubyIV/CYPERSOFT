package com.example.demo.config;

import com.example.demo.exception.CustomException;
import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null)
            throw new CustomException.NotFoundException("Not found user " + username);
        return userEntity;
    }

    private Collection<GrantedAuthority> mapRolesToAuthorities(List<RoleEntity> roleEntities) {
        return roleEntities.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
    }
}