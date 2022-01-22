package com.urlshortener.service.mapper;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dto.NewUrlDto;
import com.urlshortener.model.Url;
import com.urlshortener.service.UrlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlMapper {
    private final UrlDao urlDao;
    private final UrlConverter urlConverter;

    public Url createFrom(NewUrlDto newUrlDto) {
        Url url = new Url();

        url.setId(urlDao.getNextId());
        url.setUrl(newUrlDto.getUrl());
        url.setShortUrl(urlConverter.encode(url.getId()));
        if (!newUrlDto.isTimeLess()) {
            url.setExpiresDate(newUrlDto.getExpiresDate());
        }

        return url;
    }
}
