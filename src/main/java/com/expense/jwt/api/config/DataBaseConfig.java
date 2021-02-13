package com.expense.jwt.api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
@Configuration
public class DataBaseConfig {

    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;

    @Value("${spring.expensedb.datasource.url}")
    private String url;

    @Value("${spring.expensedb.datasource.username}")
    private String username;

    @Value("${spring.expensedb.datasource.password}")
    private String password;


    @Value("${spring.datasource.hikari.maximumPoolSize}")
    private int maximumPoolSize;

    @Value("${spring.datasource.hikari.minimumIdle}")
    private int minimumIdle;

    @Value("${spring.datasource.hikari.connectionTimeout}")
    private int connectionTimeout;

    @Value("${spring.datasource.hikari.idleTimeout}")
    private int idleTimeout;


    @Bean(name = "expenseDataSource")
    public DataSource expenseDataSource() throws SQLException {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(url);
        hikariConfig.setDriverClassName(driverClassName);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setMaximumPoolSize(maximumPoolSize);
        hikariConfig.setMinimumIdle(minimumIdle);
        hikariConfig.setIdleTimeout(idleTimeout);
        hikariConfig.setConnectionTimeout(connectionTimeout);

        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource expenseDataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(expenseDataSource);
        return jdbcTemplate;
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource expenseDataSource) {
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(expenseDataSource);
        return namedParameterJdbcTemplate;
    }


}
