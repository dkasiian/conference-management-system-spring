package com.dkasiian.security.jwt;

import org.springframework.security.core.AuthenticationException;

class JwtAuthenticationException extends AuthenticationException {

    JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    JwtAuthenticationException(String msg) {
        super(msg);
    }
}
