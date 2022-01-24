package com.urlshortener.dto.mapper;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dto.model.UrlDto;
import com.urlshortener.model.Url;
import com.urlshortener.service.UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlMapper {
    private final UrlDao urlDao;
    private final UrlConverter urlConverter;

    public Url createFrom(UrlDto urlDto) {
        Url url = new Url();

        url.setId(urlDao.getNextId());
        url.setUrl(urlDto.getUrl());
        url.setShortUrl(urlConverter.encode(url.getId()));
        if (!urlDto.isTimeLess()) {
            url.setExpiresDate(urlDto.getExpiresDate());
        }

        return url;
    }

    public UrlDto createFrom(Url url) {
        UrlDto urlDto = new UrlDto();

        urlDto.setId(url.getId());
        urlDto.setUrl(url.getUrl());
        urlDto.setShortUrl(url.getShortUrl());
        if (!url.isTimeLess()) {
            urlDto.setExpiresDate(url.getExpiresDate());
        }

        return urlDto;
    }
}
