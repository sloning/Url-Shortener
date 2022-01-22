package com.urlshortener.dao;

import com.urlshortener.model.Role;
import com.urlshortener.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<User> get(long id) {
        String sql = "select * from \"user\" where id = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToUser), id);
    }

    public Optional<User> get(String username) {
        String sql = "select * from \"user\" where username = ?";

        return jdbcTemplate.query(sql, ResultSetExtractorFactory.optionalExtractor(this::mapRowToUser), username);
    }

    public int save(User user) {
        String sql = "insert into \"user\"(username, password, role) values(?, ?, ?)";

        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getRole().toString());
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role")));

        return user;
    }
}
