package org.webflux.microservice.repository;

import org.webflux.microservice.model.AddressEntity;
import reactor.core.publisher.Mono;

public interface AddressRepositoryCustom {
    Mono<AddressEntity> saveOrReturnDuplicate(AddressEntity addressEntity);
}