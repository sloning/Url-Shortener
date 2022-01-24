package com.urlshortener.controller;

import com.urlshortener.dto.model.UserDto;
import com.urlshortener.dto.response.Response;
import com.urlshortener.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public Response<Map<String, String>> login(@Valid @RequestBody final UserDto userDto) {
        return new Response<>(authService.login(userDto));
    }

    @PostMapping("/register")
    public Response<Map<String, String>> register(@Valid @RequestBody final UserDto userDto) {
        return new Response<>(authService.register(userDto));
    }
}
