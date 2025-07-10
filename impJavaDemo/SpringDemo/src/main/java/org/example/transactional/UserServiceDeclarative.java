package org.example.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceDeclarative {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void createUser() {
        jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)", 1, "Alice");
        System.out.println("插入用户 Alice");

        if (true) {
            throw new RuntimeException("模拟异常");
        }

        jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)", 2, "Bob");
    }
}
