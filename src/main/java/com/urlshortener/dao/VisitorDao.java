package com.urlshortener.dao;

import com.urlshortener.model.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
@RequiredArgsConstructor
public class VisitorDao {
    private final JdbcTemplate jdbcTemplate;

    public boolean existsByUrlIdAndIp(long urlId, String ip) {
        String sql = "select exists(select 1 from unique_visit where url_id = ? and ip_address = ?)";

        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sql, Boolean.class, urlId, ip));
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
