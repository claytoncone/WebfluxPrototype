package org.webflux.microservice.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.transaction.ReactiveTransactionManager;
import reactor.util.annotation.NonNull;

@Configuration
@EnableR2dbcRepositories
@Profile("!test")
public class R2dbcConfig extends AbstractR2dbcConfiguration {

    @Value("${r2dbc.url}")
    private String r2dbcUrl;

    @Value("${r2dbc.username}")
    private String username;

    @Value("${r2dbc.password}")
    private String password;

    @Bean
    @NonNull
    @Override
    public ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.withUrl(r2dbcUrl)
                .username(username)
                .password(password)
                .build();
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean(name = "mainDatabaseClient")
    public DatabaseClient databaseClient(ConnectionFactory connectionFactory) {
        return DatabaseClient.create(connectionFactory);
    }
}