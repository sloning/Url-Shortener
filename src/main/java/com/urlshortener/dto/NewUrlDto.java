package com.urlshortener.dto;

import lombok.Data;

import java.util.Date;

@Data
public class NewUrlDto {
    private String url;
    private boolean timeLess = true;
    private Date validUntil;
}
