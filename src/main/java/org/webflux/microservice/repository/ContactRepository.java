package org.webflux.microservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.webflux.microservice.model.ContactEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ContactRepository extends ReactiveCrudRepository<ContactEntity, Long>, CustomContactRepository {

    Mono<ContactEntity> findContactByEmail(String email);

    Flux<ContactEntity> findContactByPhone(String phone);
}
