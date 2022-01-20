package com.urlshortener.dao;

import com.urlshortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Url> get(String shortUrl) {
        String sql = "select * from url where short_url = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToUrl), shortUrl);
    }

    public int save(Url url) {
        String sql = "insert into url(url, short_url, valid_until, unique_visits, number_of_visits) " +
                "values(?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, url.getUrl(), url.getShortUrl(), url.getValidUntil(), url.getUniqueVisits(),
                url.getNumberOfVisits());
    }

    public int update(Url url) {
        String sql = "update url set unique_visits = ?, number_of_visits = ? where id = ?";

        return jdbcTemplate.update(sql, url.getUniqueVisits(), url.getNumberOfVisits(), url.getId());
    }

    public int delete(Url url) {
        String sql = "delete from url where id = ?";

        return jdbcTemplate.update(sql, url.getId());
    }

    public int deleteInvalidUrls() {
        String sql = "delete from url where valid_until <= current_timestamp";

        return jdbcTemplate.update(sql);
    }

    private Url mapRowToUrl(ResultSet rs, int rowNum) throws SQLException {
        Url url = new Url();

        url.setId(rs.getLong("id"));
        url.setUrl(rs.getString("url"));
        url.setShortUrl(rs.getString("short_url"));
        url.setValidUntil(rs.getDate("valid_until"));
        url.setUniqueVisits(rs.getLong("unique_visits"));
        url.setNumberOfVisits(rs.getLong("number_of_visits"));

        return url;
    }
}
