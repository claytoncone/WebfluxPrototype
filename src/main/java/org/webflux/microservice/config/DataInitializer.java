package org.webflux.microservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Profile("!test")
public class DataInitializer {

    @Bean
    public Mono<Void> initializeTestData(DatabaseClient databaseClient) {
        ResourceDatabasePopulator schema = new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        ResourceDatabasePopulator data = new ResourceDatabasePopulator(new ClassPathResource("seed_data.sql"));
        return Mono.from(schema.populate(databaseClient.getConnectionFactory()))
                .then(Mono.from(data.populate(databaseClient.getConnectionFactory())));
    }
}