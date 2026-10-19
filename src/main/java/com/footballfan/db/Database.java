package com.footballfan.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Создаёт пул соединений и JdbcTemplate.
 * Параметры подключения берутся из переменных окружения (с значениями по умолчанию
 * для docker-compose), поэтому пароли не хранятся в коде.
 */
public final class Database {

    private static HikariDataSource dataSource;
    private static JdbcTemplate jdbcTemplate;

    private Database() {}

    public static synchronized JdbcTemplate jdbc() {
        if (jdbcTemplate == null) {
            HikariConfig cfg = new HikariConfig();
            cfg.setJdbcUrl(env("DB_URL", "jdbc:postgresql://localhost:5432/football"));
            cfg.setUsername(env("DB_USER", "football"));
            cfg.setPassword(env("DB_PASSWORD", "football"));
            cfg.setMaximumPoolSize(5);
            cfg.setConnectionTimeout(3000);
            dataSource = new HikariDataSource(cfg);
            jdbcTemplate = new JdbcTemplate(dataSource);
        }
        return jdbcTemplate;
    }

    public static DataSource dataSource() {
        jdbc();
        return dataSource;
    }

    public static synchronized void close() {
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
            jdbcTemplate = null;
        }
    }

    private static String env(String name, String defaultValue) {
        String value = System.getenv(name);
        return (value == null || value.isBlank()) ? defaultValue : value;
    }
}
