package org.webflux.microservice.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import org.webflux.microservice.model.AddressEntity;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
public class AddressRepositoryCustomImpl implements AddressRepositoryCustom {

    @Autowired
    private DatabaseClient databaseClient;

    @Override
    public Mono<AddressEntity> saveOrReturnDuplicate(AddressEntity addressEntity) {
        return findAddress(addressEntity)
                .switchIfEmpty(
                        insertAddress(addressEntity)
                                .then(findAddress(addressEntity))
                );
    }

    private Mono<AddressEntity> findAddress(AddressEntity addressEntity) {
        return databaseClient.sql("SELECT * FROM address WHERE country = :country AND address_line1 = :addressLine1 AND address_line2 = :addressLine2 AND city = :city AND state = :state AND zip = :zip")
                .bind("country", addressEntity.getCountry())
                .bind("addressLine1", addressEntity.getAddressLine1())
                .bind("addressLine2", addressEntity.getAddressLine2() != null ? addressEntity.getAddressLine2() : "")
                .bind("city", addressEntity.getCity())
                .bind("state", addressEntity.getState())
                .bind("zip", addressEntity.getPostalCode())
                .map((row, rowMetadata) -> new AddressEntity(
                        row.get("id", Long.class),
                        row.get("country", String.class),
                        row.get("zip", String.class),
                        row.get("address_line1", String.class),
                        row.get("address_line2", String.class),
                        row.get("city", String.class),
                        row.get("state", String.class),
                        row.get("version", Long.class),
                        row.get("created_date", LocalDateTime.class),
                        row.get("last_modified_date", LocalDateTime.class)
                ))
                .one();
    }

    private Mono<Void> insertAddress(AddressEntity addressEntity) {
        return databaseClient.sql("INSERT INTO address (country, address_line1, address_line2, city, state, zip) VALUES (:country, :addressLine1, :addressLine2, :city, :state, :zip)")
                .bind("country", addressEntity.getCountry())
                .bind("addressLine1", addressEntity.getAddressLine1())
                .bind("addressLine2", addressEntity.getAddressLine2() != null ? addressEntity.getAddressLine2() : "")
                .bind("city", addressEntity.getCity())
                .bind("state", addressEntity.getState())
                .bind("zip", addressEntity.getPostalCode())
                .then();
    }
}