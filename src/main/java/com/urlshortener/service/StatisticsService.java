package com.urlshortener.service;

import com.urlshortener.dao.UrlDao;
import com.urlshortener.dao.VisitorDao;
import com.urlshortener.model.Url;
import com.urlshortener.model.Visitor;
import com.urlshortener.util.HttpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final UrlDao urlDao;
    private final VisitorDao visitorDao;

    public List<Url> get() {
        return urlDao.getAll();
    }

    public void updateCounters(Url url, HttpServletRequest request) {
        Optional<Visitor> optionalVisitor = visitorDao.getByUrlId(url.getId());

        if (optionalVisitor.isEmpty()) {
            Visitor newVisitor = new Visitor();

            String ipAddress = HttpUtils.getRequestIp(request);
            newVisitor.setIpAddress(ipAddress);
            newVisitor.setUrlId(url.getId());
            visitorDao.save(newVisitor);

            url.setUniqueVisits(url.getUniqueVisits() + 1);
        }

        url.setNumberOfVisits(url.getNumberOfVisits() + 1);

        urlDao.update(url);
    }
}
