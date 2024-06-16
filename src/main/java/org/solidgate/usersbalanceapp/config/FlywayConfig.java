package org.solidgate.usersbalanceapp.config;

import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {

    @Bean(initMethod = "migrate")
    public Flyway flyway(HikariDataSource dataSource) {
        String location = "/db/migration/";

        return Flyway.configure()
                .dataSource(dataSource)
                .locations(location)
                .baselineOnMigrate(true)
                .load();
    }
}
