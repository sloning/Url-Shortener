package com.urlshortener.controller;

import com.urlshortener.dto.NewUrlDto;
import com.urlshortener.model.Url;
import com.urlshortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/url")
public class UrlController {
    private final UrlService urlService;

    @PostMapping()
    public ResponseEntity<Url> createNewShortUrl(@RequestBody @Valid final NewUrlDto newUrlDto) {
        return ResponseEntity.ok(urlService.createUrl(newUrlDto));
    }

    @GetMapping("/{shortUrl}")
    @Cacheable(value = "urls", key = "#shortUrl", sync = true)
    public String redirectToUrl(@PathVariable String shortUrl) {
        return "redirect:" + urlService.getOriginalUrl(shortUrl);
    }

    @GetMapping()
    public ResponseEntity<List<Url>> getAll() {
        return ResponseEntity.ok(urlService.getAll());
    }

    @DeleteMapping()
    public ResponseEntity<Void> deleteUrl(@RequestBody Long urlId) {
        urlService.delete(urlId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/expired")
    public ResponseEntity<Integer> deleteExpiredUrls() {
        return ResponseEntity.ok(urlService.deleteExpiredUrls());
    }
}
