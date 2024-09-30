package org.webflux.microservice.repository;

import reactor.core.publisher.Mono;

public interface CustomClientRepository {
    Mono<Void> deleteClientAndContactById(Long id);
}
