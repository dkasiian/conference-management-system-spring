package com.dkasiian.security.jwt;

import com.dkasiian.model.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;

public final class JwtUserFactory {

    public JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getPassword(),
                new ArrayList<GrantedAuthority>() { {
                    add( new SimpleGrantedAuthority(user.getRole().name()) );
                }},
                true
        );
    }
}