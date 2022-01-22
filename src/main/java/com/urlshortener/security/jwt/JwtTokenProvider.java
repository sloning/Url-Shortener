package com.urlshortener.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.urlshortener.exception.JwtAuthenticationException;
import com.urlshortener.model.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.urlshortener.config.AuthenticationConfigConstants.*;


@Component
public class JwtTokenProvider {
    private final Algorithm algorithm;

    public JwtTokenProvider() {
        algorithm = Algorithm.HMAC256(SECRET);
    }

    public String createToken(User user) {
        Map<String, Object> payloadClaims = new HashMap<>();
        payloadClaims.put("userId", user.getId());
        payloadClaims.put("role", user.getRole().toString());

        return JWT.create()
                .withPayload(payloadClaims)
                .withIssuer("urlShortener")
                .sign(algorithm);
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(HEADER_STRING);
        if (bearerToken != null && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean verifyToken(String token) {
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("urlShortener")
                    .build();
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw new JwtAuthenticationException("Your session is invalid. Please reauthenticate.");
        }
    }

    public Claim getUserId(String token) {
        return JWT.decode(token).getClaim("userId");
    }

    public Claim getUserRole(String token) {
        return JWT.decode(token).getClaim("role");
    }

    public Authentication getAuthentication(String token) {
        JwtUser userDetails = JwtUserFactory.create(getUserId(token).asLong(), getUserRole(token).asString());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
