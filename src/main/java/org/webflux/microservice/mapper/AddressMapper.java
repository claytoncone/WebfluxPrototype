package org.webflux.microservice.mapper;

import org.mapstruct.*;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.rest.api.Address;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class AddressMapper {

    public abstract Address toDTO(AddressEntity addressEntity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    public abstract AddressEntity toModel(Address address);
}