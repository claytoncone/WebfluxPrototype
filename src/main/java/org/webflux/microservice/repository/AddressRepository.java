package org.webflux.microservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.webflux.microservice.model.AddressEntity;
import reactor.core.publisher.Flux;

public interface AddressRepository extends R2dbcRepository<AddressEntity, Long>, AddressRepositoryCustom {
    @Query("SELECT * FROM address WHERE zip = :zip")
    Flux<AddressEntity> findAddressesByPostalCode(String zip);
    Flux<AddressEntity> findAddressesByState(String state);
}
