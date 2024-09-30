package org.webflux.microservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.webflux.microservice.model.ContactEntity;
import org.webflux.microservice.rest.api.Contact;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ContactMapper {

    Contact toDTO(ContactEntity contactEntity);
}
