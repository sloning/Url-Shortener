package com.urlshortener.controller;

import com.urlshortener.dto.NewUrlDto;
import com.urlshortener.model.Url;
import com.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class UrlController {
    private final UrlService urlService;

    @PostMapping()
    public ResponseEntity<Url> createNewShortUrl(@RequestBody final NewUrlDto newUrlDto) {
        return ResponseEntity.ok(urlService.createUrl(newUrlDto));
    }

    @GetMapping("/{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public String redirectToUrl(@PathVariable String shortUrl) {
        return "redirect:" + urlService.getOriginalUrl(shortUrl);
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUrl(@RequestBody Long urlId) {
        urlService.delete(urlId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/expiredUrls")
    public ResponseEntity<Integer> deleteExpiredUrls() {
        return ResponseEntity.ok(urlService.deleteExpiredUrls());
    }
}
