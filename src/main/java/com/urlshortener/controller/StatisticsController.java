package com.urlshortener.controller;

import com.urlshortener.dto.StatisticsDto;
import com.urlshortener.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistic")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping()
    public ResponseEntity<List<StatisticsDto>> get() {
        return ResponseEntity.ok(statisticsService.get());
    }
}
