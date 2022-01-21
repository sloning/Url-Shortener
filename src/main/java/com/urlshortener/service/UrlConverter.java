package com.urlshortener.service;

import org.springframework.stereotype.Component;

@Component
public class UrlConverter {
    private static final String allowedString = "abcdefghijklmnopqrstuvwxyz0123456789";
    private final char[] allowedCharacters = allowedString.toCharArray();
    private final int base = allowedCharacters.length;

    public String encode(long input) {
        var encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }
}
