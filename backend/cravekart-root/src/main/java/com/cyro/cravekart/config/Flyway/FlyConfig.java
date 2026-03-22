package com.cyro.cravekart.config.Flyway;

import javax.sql.DataSource;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlyConfig {
  public FlyConfig(DataSource dataSource) {
    Flyway flyway =
        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .baselineOnMigrate(true)
            .baselineVersion("1")
            .baselineDescription("Initial schema from Hibernate ddl-auto")
            .load();

    flyway.migrate();
  }
}
