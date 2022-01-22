package com.urlshortener.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserDto {
    @Size(min = 4)
    private String username;
    @Size(min = 4)
    private String password;
}
