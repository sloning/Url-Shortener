package com.urlshortener.service;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dto.NewUrlDto;
import com.urlshortener.exception.EntityNotDeletedException;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import com.urlshortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlDao urlDao;
    private final UrlConverter urlConverter;

    public String getOriginalUrl(String shortUrl) {
        Url url = urlDao.getByShortUrl(shortUrl).orElseThrow(() -> new EntityNotFoundException(
                String.format("Entity with url: %s was not found", shortUrl)));

        url.setNumberOfVisits(url.getNumberOfVisits() + 1);
        urlDao.update(url);

        return url.getUrl();
    }

    public List<Url> getAll() {
        return urlDao.getAll();
    }

    public Url createUrl(NewUrlDto newUrlDto) {
        Optional<Url> optionalUrl = urlDao.getByFullUrl(newUrlDto.getUrl());
        if (optionalUrl.isPresent()) {
            Url url = optionalUrl.get();
            if (url.isTimeLess() && newUrlDto.isTimeLess()) {
                return url;
            }
        }

        Url url = new Url();
        url.setId(urlDao.getNextId());
        url.setUrl(newUrlDto.getUrl());
        url.setShortUrl(urlConverter.encode(url.getId()));
        if (!newUrlDto.isTimeLess()) {
            url.setExpiresDate(newUrlDto.getExpiresDate());
        }

        save(url);
        return url;
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
