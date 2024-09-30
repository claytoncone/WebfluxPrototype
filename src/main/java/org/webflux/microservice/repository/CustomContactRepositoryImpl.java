package org.webflux.microservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.webflux.microservice.model.ContactEntity;
import reactor.core.publisher.Mono;

@Component
public class CustomContactRepositoryImpl implements CustomContactRepository {
    private final DatabaseClient databaseClient;

    @Autowired
    public CustomContactRepositoryImpl(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Void> insert(ContactEntity contactEntity) {
        return databaseClient.sql("INSERT INTO contact (client_id, email, phone, mobile, fax) VALUES (:clientId, :email, :phone, :mobile, :fax)")
                .bind("clientId", contactEntity.getClientId())
                .bind("email", contactEntity.getEmail())
                .bind("phone", contactEntity.getPhone()  != null ? contactEntity.getPhone() : "")
                .bind("mobile", contactEntity.getMobile() != null ? contactEntity.getMobile() : "")
                .bind("fax", contactEntity.getFax() != null ? contactEntity.getFax() : "")
                .then();
    }
}
