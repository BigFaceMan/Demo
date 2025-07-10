package org.example.transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class UserServiceProgrammatic {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void createUser() {
        transactionTemplate.execute(status -> {
            jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)", 3, "Charlie");
            System.out.println("插入用户 Charlie");

            if (true) {
                throw new RuntimeException("模拟异常");
            }

            jdbcTemplate.update("INSERT INTO users(id, name) VALUES (?, ?)", 4, "David");
            return null;
        });
    }
}
