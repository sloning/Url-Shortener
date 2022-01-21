package com.urlshortener.model;

import lombok.Data;

import java.util.Date;

@Data
public class Url {
    private long id;
    private String url;
    private String shortUrl;
    private Date expiresDate;
    private boolean timeLess = true;
    private long numberOfVisits;
    private long uniqueVisits;

    public void setExpiresDate(Date date) {
        expiresDate = date;
        timeLess = date == null;
    }
}
