package com.example.demo.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_Users")
public class UserEntity extends BaseModel implements UserDetails {

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String email;

    private String phone;

    private boolean isEnable;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_UserRole",
            joinColumns = @JoinColumn(name = "id_User"),
            inverseJoinColumns = @JoinColumn(name = "id_Role")
    )
    private List<RoleEntity> listRole = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return listRole.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toList());
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnable;
    }
}
