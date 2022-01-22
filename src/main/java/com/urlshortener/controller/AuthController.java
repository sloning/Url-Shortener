package com.urlshortener.controller;

import com.urlshortener.dto.UserDto;
import com.urlshortener.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody final UserDto userDto) {
        return ResponseEntity.ok(authService.login(userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody final UserDto userDto) {
        return ResponseEntity.ok(authService.register(userDto));
    }
}
