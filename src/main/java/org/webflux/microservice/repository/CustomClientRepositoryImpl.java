package org.webflux.microservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.webflux.microservice.model.ClientEntity;
import reactor.core.publisher.Mono;

@Component
public class CustomClientRepositoryImpl implements CustomClientRepository {

    private final DatabaseClient databaseClient;

    @Autowired
    public CustomClientRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Void> deleteClientAndContactById(Long id) {
        return databaseClient.sql("DELETE FROM contact WHERE client_id = :id")
                .bind("id", id)
                .then()
                .then(databaseClient.sql("DELETE FROM client WHERE id = :id")
                        .bind("id", id)
                        .then());
    }
}