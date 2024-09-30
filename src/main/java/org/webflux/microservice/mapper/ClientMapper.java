package org.webflux.microservice.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.rest.api.Client;
import org.webflux.microservice.rest.api.NewClient;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AddressMapper.class, ContactMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class ClientMapper {

    public abstract Client toDTO(ClientEntity clientEntity);

    @Mapping(target = "version", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "contactInfo", ignore = true)

    public abstract ClientEntity toModel(NewClient newClient);

}
