package org.webflux.microservice.repository;

import org.webflux.microservice.model.ContactEntity;
import reactor.core.publisher.Mono;

public interface CustomContactRepository {
    /*
     * This should not be necessary, there is a bug in the R2DBC implementation of saveAll that is attempting to
     * perform an update on a contact record that does not exist.
     */
    Mono<Void> insert(ContactEntity contactEntity);
}
