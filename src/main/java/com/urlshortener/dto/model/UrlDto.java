package com.urlshortener.dto.model;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Data
public class UrlDto {
    private long id;
    @URL(message = "Invalid url")
    private String url;
    private String shortUrl;
    private boolean timeLess = true;
    private Date expiresDate;
}
