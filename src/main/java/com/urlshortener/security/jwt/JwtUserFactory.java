package com.urlshortener.security.jwt;

import com.urlshortener.model.Role;

public class JwtUserFactory {
    public JwtUserFactory() {
    }


    public static JwtUser create(Long id, String role) {
        return new JwtUser(id, null, null, Role.valueOf(role));
    }
}
