package com.urlshortener.service.mapper;

import com.urlshortener.dto.StatisticsDto;
import com.urlshortener.model.Url;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StatisticsMapper {
    public List<StatisticsDto> createFrom(List<Url> urls) {
        return urls.stream().map(url -> {
            StatisticsDto statisticsDto = new StatisticsDto();
            statisticsDto.setUrlId(url.getId());
            statisticsDto.setUrl(url.getUrl());
            statisticsDto.setNumberOfVisits(url.getNumberOfVisits());
            statisticsDto.setUniqueVisits(url.getUniqueVisits());
            return statisticsDto;
        }).collect(Collectors.toList());
    }
}
