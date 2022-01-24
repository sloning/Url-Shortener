package com.urlshortener.model;

import lombok.Data;

@Data
public class Visitor {
    private String ipAddress;
    private long urlId;
}
