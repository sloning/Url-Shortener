package com.urlshortener.service;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dto.mapper.UrlMapper;
import com.urlshortener.dto.model.UrlDto;
import com.urlshortener.exception.EntityNotDeletedException;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import com.urlshortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlDao urlDao;
    private final UrlMapper urlMapper;
    private final StatisticsService statisticsService;

    public String getOriginalUrl(String shortUrl) {
        Url url = urlDao.getByShortUrl(shortUrl).orElseThrow(() -> new EntityNotFoundException(
                String.format("Entity with url: %s was not found", shortUrl)));

        statisticsService.updateCounters(url);

        return url.getUrl();
    }

    public UrlDto createUrl(UrlDto urlDto) {
        Optional<Url> optionalUrl = urlDao.getByFullUrl(urlDto.getUrl());
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            if (url.isTimeLess() && urlDto.isTimeLess()) {
                return urlMapper.createFrom(url);
            }
        }

        Url url = urlMapper.createFrom(urlDto);
        save(url);
        return urlMapper.createFrom(url);
    }

    public void delete(Long id) {
        if (urlDao.delete(id) == 0) {
            throw new EntityNotDeletedException(String.format("Url with id: %d was not deleted", id));
        }
    }

    public int deleteExpiredUrls() {
        return urlDao.deleteExpiredUrls();
    }

    public void save(Url url) {
        if (urlDao.save(url) == 0) {
            throw new EntityNotSavedException(String.format("Url: %s can not be created", url.getUrl()));
        }
    }
}
