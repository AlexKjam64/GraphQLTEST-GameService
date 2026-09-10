package dev1.alexkjam64.SpringBootProject.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DataSourceController {
    @Bean
    public HikariDataSource dataSource(){
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver"); 
        // jdbc:postgresql://@pellefant.db.elephantsql.com:5432/cwkqmdql?user=cwkqmdql&password=SsVqwdLxQObgaJAYu68O-8gTY1VmS9LX
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/NintendoAccount?user=postgres&password=u2r94970203");
        return new HikariDataSource(config); 
    }
}
