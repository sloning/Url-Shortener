package com.urlshortener.controller;

import com.urlshortener.dto.response.Response;
import com.urlshortener.model.Url;
import com.urlshortener.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping()
    public Response<List<Url>> get() {
        return new Response<>(statisticsService.get());
    }
}
