package com.urlshortener.dto;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.util.Date;

@Data
public class NewUrlDto {
    @URL(message = "Invalid url")
    private String url;
    private boolean timeLess = true;
    private Date validUntil;
}
