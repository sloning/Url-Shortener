package com.urlshortener.dto;

import lombok.Data;

@Data
public class StatisticsDto {
    private long urlId;
    private String url;
    private long numberOfVisits;
    private long uniqueVisits;
}
