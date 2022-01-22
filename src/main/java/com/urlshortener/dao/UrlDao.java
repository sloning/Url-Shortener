package com.urlshortener.dao;

import com.urlshortener.model.Url;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Url> getByShortUrl(String url) {
        String sql = "select * from url where short_url = ? and " +
                "(expires_date > current_timestamp or expires_date is null)";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToUrl), url);
    }

    public Optional<Url> getByFullUrl(String url) {
        String sql = "select * from url where url = ? and " +
                "(expires_date > current_timestamp or expires_date is null)";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToUrl), url);
    }

    public long getNextId() {
        String sql = "select nextval('url_id_seq')";

        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    public List<Url> getAll() {
        String sql = "select * from url";

        return jdbcTemplate.query(sql, this::mapRowToUrl);
    }

    public int save(Url url) {
        String sql = "insert into url(id, url, short_url, expires_date, unique_visits, number_of_visits) " +
                "values(?, ?, ?, ?, ?, ?)";

        return jdbcTemplate.update(sql, url.getId(), url.getUrl(), url.getShortUrl(), url.getExpiresDate(),
                url.getUniqueVisits(), url.getNumberOfVisits());
    }

    public int update(Url url) {
        String sql = "update url set expires_date = ?, unique_visits = ?, number_of_visits = ? where id = ?";

        return jdbcTemplate.update(sql, url.getExpiresDate(), url.getUniqueVisits(), url.getNumberOfVisits(),
                url.getId());
    }

    public int delete(Long id) {
        String sql = "delete from url where id = ?";

        return jdbcTemplate.update(sql, id);
    }

    public int deleteExpiredUrls() {
        String sql = "delete from url where expires_date <= current_timestamp";

        return jdbcTemplate.update(sql);
    }

    private Url mapRowToUrl(ResultSet rs, int rowNum) throws SQLException {
        Url url = new Url();

        url.setId(rs.getLong("id"));
        url.setUrl(rs.getString("url"));
        url.setShortUrl(rs.getString("short_url"));
        url.setExpiresDate(rs.getDate("expires_date"));
        url.setUniqueVisits(rs.getLong("unique_visits"));
        url.setNumberOfVisits(rs.getLong("number_of_visits"));

        return url;
    }
}
