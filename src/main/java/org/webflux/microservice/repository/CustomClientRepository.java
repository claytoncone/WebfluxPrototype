package org.webflux.microservice.repository;

import org.webflux.microservice.model.ClientEntity;
import reactor.core.publisher.Mono;

public interface CustomClientRepository {
    Mono<Void> deleteClientAndContactById(Long id);
}
