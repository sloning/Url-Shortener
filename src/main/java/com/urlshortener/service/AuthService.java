package com.urlshortener.service;

import com.urlshortener.dto.model.UserDto;
import com.urlshortener.exception.EntityAlreadyExistsException;
import com.urlshortener.model.User;
import com.urlshortener.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public Map<String, String> register(UserDto userDto) {
        if (userService.isUserExists(userDto.getUsername())) {
            throw new EntityAlreadyExistsException(
                    String.format("User with username: %s already exists", userDto.getUsername()));
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.save(user);

        return getToken(user);
    }

    public Map<String, String> login(UserDto userDto) {
        User user = userService.getByUsernameAndPassword(userDto.getUsername(), userDto.getPassword());

        return getToken(user);
    }

    private Map<String, String> getToken(User user) {
        String token = jwtTokenProvider.createToken(user);

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}
