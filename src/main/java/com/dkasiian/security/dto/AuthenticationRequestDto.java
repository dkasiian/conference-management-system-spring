package com.dkasiian.security.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthenticationRequestDto {

    @NotEmpty(message = "*Please provide username (login)")
    private String username;

    @NotEmpty(message = "*Please provide password")
    private String password;
}
