package com.gilbertguevara.testing;

import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder {
    private final JdbcTemplate jdbcTemplate;

    public DatabaseSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void seed() {
        jdbcTemplate.execute("INSERT INTO person (id, first_name, last_name) VALUES (gen_random_uuid(), 'Peter', 'Pan')");
    }
}
