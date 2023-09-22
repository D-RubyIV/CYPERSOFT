package com.example.demo;

import com.example.demo.model.RoleEntity;
import com.example.demo.model.UserEntity;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class initDataBase {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init(){
        if (roleRepository.findByCode("ROLE_ADMIN") == null){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setCode("ROLE_ADMIN");
            roleRepository.save(roleEntity);
        }
        if (roleRepository.findByCode("ROLE_USER") == null){
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setCode("ROLE_USER");
            roleRepository.save(roleEntity);
        }
        if (userRepository.findByUsername("admin") == null){
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername("admin");
            userEntity.setPassword(passwordEncoder.encode("123456"));
            userEntity.setEmail("phamhaanh2k4.php@gmail.com");
            userEntity.setPhone("0833486936");
            userEntity.setFirstname("Pham");
            userEntity.setLastname("Ha Anh");
            userEntity.setEnable(true);
            userEntity.setListRole(List.of(roleRepository.findByCode("ROLE_ADMIN")));
            userRepository.save(userEntity);
        }
    }
}
