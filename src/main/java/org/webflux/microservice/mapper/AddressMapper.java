package org.webflux.microservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.rest.api.Address;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AddressMapper {
    Address toDTO(AddressEntity addressEntity);
}
