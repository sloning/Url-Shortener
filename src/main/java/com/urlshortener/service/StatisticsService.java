package com.urlshortener.service;

import com.urlshortener.dto.StatisticsDto;
import com.urlshortener.service.mapper.StatisticsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UrlService urlService;
    private final StatisticsMapper statisticsMapper;

    public List<StatisticsDto> get() {
        return statisticsMapper.createFrom(urlService.getAll());
    }
}
