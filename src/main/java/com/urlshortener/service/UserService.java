package com.urlshortener.service;

import com.urlshortener.dao.UserDao;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import com.urlshortener.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public boolean isUserExists(String username) {
        Optional<User> optionalUser = userDao.get(username);
        return optionalUser.isPresent();
    }

    public boolean isUserExists(long id) {
        Optional<User> optionalUser = userDao.get(id);
        return optionalUser.isPresent();
    }

    public void save(User user) {
        if (userDao.save(user) == 0) {
            throw new EntityNotSavedException(String.format("User: %s can not be saved", user.getUsername()));
        }
    }

    public User getByUsername(String username) {
        return userDao.get(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User: %s was not found", username)));
    }

    public User getByUsernameAndPassword(String username, String password) {
        User user = getByUsername(username);

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new BadCredentialsException("Wrong password");
    }
}
