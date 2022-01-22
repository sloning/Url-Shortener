package com.urlshortener.security;

import com.urlshortener.security.jwt.JwtUser;
import com.urlshortener.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final UserService userService;

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public long getPlayerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            JwtUser jwtUser = (JwtUser) auth.getPrincipal();
            if (userService.isUserExists(jwtUser.getId())) {
                return jwtUser.getId();
            }
        }
        throw new BadCredentialsException("You do not have permission to view this");
    }
}
