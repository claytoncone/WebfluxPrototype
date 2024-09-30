package org.webflux.microservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.webflux.microservice.model.ClientEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, Long>, CustomClientRepository {
    Flux<ClientEntity> findClientBySurname(String surname);

    Flux<ClientEntity> findClientByCompany(String company);

    @Query("SELECT c.* FROM client c JOIN contact ct ON c.id = ct.client_id WHERE ct.email = :email")
    Mono<ClientEntity> findClientByEmail(String email);

}
