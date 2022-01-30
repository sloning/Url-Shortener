package com.urlshortener.controller;

import com.urlshortener.dto.model.UrlDto;
import com.urlshortener.dto.response.Response;
import com.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/url")
public class UrlController {
    private final UrlService urlService;

    @GetMapping("/{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public void redirectToUrl(@PathVariable String shortUrl,
                              HttpServletResponse response) throws IOException {
        response.sendRedirect(urlService.getOriginalUrl(shortUrl));
    }

    @PostMapping()
    public Response<UrlDto> createNewShortUrl(@RequestBody @Valid final UrlDto urlDto) {
        return new Response<>(urlService.createUrl(urlDto));
    }

    @DeleteMapping()
    public Response<Void> deleteUrl(@RequestBody Long urlId) {
        urlService.delete(urlId);
        return new Response<>("Url was successfully deleted");
    }

    @DeleteMapping("/expired")
    public Response<Void> deleteExpiredUrls() {
        return new Response<>(urlService.deleteExpiredUrls() + " urls were successfully deleted");
    }
}
