package com.urlshortener.service;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dao.VisitorDao;
import com.urlshortener.dto.NewUrlDto;
import com.urlshortener.exception.EntityNotDeletedException;
import com.urlshortener.exception.EntityNotFoundException;
import com.urlshortener.exception.EntityNotSavedException;
import com.urlshortener.model.Url;
import com.urlshortener.model.Visitor;
import com.urlshortener.service.mapper.UrlMapper;
import com.urlshortener.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlDao urlDao;
    private final VisitorDao visitorDao;
    private final UrlMapper urlMapper;

    public String getOriginalUrl(String shortUrl, HttpServletRequest httpServletRequest) {
        Url url = urlDao.getByShortUrl(shortUrl).orElseThrow(() -> new EntityNotFoundException(
                String.format("Entity with url: %s was not found", shortUrl)));

        updateCounters(url, httpServletRequest);

        return url.getUrl();
    }

    private void updateCounters(Url url, HttpServletRequest httpServletRequest) {
        Optional<Visitor> optionalVisitor = visitorDao.getByUrlId(url.getId());

        if (optionalVisitor.isEmpty()) {
            Visitor newVisitor = new Visitor();

            String ipAddress = HttpUtils.getRequestIp(httpServletRequest);
            newVisitor.setIpAddress(ipAddress);
            newVisitor.setUrlId(url.getId());
            visitorDao.save(newVisitor);

            url.setUniqueVisits(url.getUniqueVisits() + 1);
        }

        url.setNumberOfVisits(url.getNumberOfVisits() + 1);

        urlDao.update(url);
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

        Url url = urlMapper.createFrom(newUrlDto);
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
