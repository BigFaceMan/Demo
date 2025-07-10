package org.example.transactional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBInitializer {

    public DBInitializer(JdbcTemplate jdbcTemplate) {
        jdbcTemplate.execute("CREATE TABLE users (id INT PRIMARY KEY, name VARCHAR(100))");
    }
}

