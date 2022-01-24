package com.urlshortener.dao;

import com.urlshortener.model.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class VisitorDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Visitor> getByUrlId(long urlId) {
        String sql = "select * from unique_visit where url_id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToVisitor), urlId);
    }

    public int save(Visitor visitor) {
        String sql = "insert into unique_visit values(?, ?)";

        return jdbcTemplate.update(sql, visitor.getIpAddress(), visitor.getUrlId());
    }

    private Visitor mapRowToVisitor(ResultSet rs, int rowNum) throws SQLException {
        Visitor visitor = new Visitor();

        visitor.setUrlId(rs.getLong("url_id"));
        visitor.setIpAddress(rs.getString("ip_address"));

        return visitor;
    }
}
