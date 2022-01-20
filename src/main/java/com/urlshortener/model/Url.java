package com.urlshortener.model;

import lombok.Data;

import java.util.Date;

@Data
public class Url {
    private long id;
    private String url;
    private String shortUrl;
    private Date validUntil;
    private long numberOfVisits;
    private long uniqueVisits;
}
