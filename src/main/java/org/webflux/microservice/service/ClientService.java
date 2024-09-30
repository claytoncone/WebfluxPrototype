package org.webflux.microservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.repository.AddressRepository;
import org.webflux.microservice.repository.ClientRepository;
import org.webflux.microservice.repository.ContactRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;

    /*
    Client has a one to many relationship with Address (multiple clients can share the same address). The client's
    contactInfo is required to be unique to the client so it is a one to one relationship.
    create client first attempts to save the address if populated. It then populates the addressId field in the
     */
    @Transactional
    public Mono<ClientEntity> create(ClientEntity clientEntity) {
        if (clientEntity.getId() != null || clientEntity.getVersion() != null) {
            return Mono.error(new IllegalArgumentException("When creating a client, the id and the version must be null"));
        }

        return Mono.just(clientEntity)
                .flatMap(c -> Mono.justOrEmpty(c.getAddress())
                        .flatMap(addressRepository::saveOrReturnDuplicate)
                        .doOnNext(savedAddress -> c.setAddressId(savedAddress.getId()))
                        .then(Mono.just(c)))
                .flatMap(clientRepository::save)
                .flatMap(savedClient -> Mono.justOrEmpty(clientEntity.getContactInfo())
                        .doOnNext(contactInfo -> contactInfo.setClientId(savedClient.getId()))
                        .flatMap(contactRepository::insert)
                        .then(Mono.just(savedClient)))
                .onErrorResume(e -> {
                    // Log the error and return a meaningful error response
                    return Mono.error(new RuntimeException("An error occurred while creating the client", e));
                });
    }

    public Flux<ClientEntity> findAll() {
        return clientRepository.findAll()
                .flatMap(this::loadRelations);
    }

    private Mono<ClientEntity> loadRelations(final ClientEntity clientEntity) {
        Mono<ClientEntity> mono = Mono.just(clientEntity)
                .zipWith(contactRepository.findById(clientEntity.getId()))
                .map(result -> result.getT1().setContactInfo(result.getT2()));

        if (clientEntity.getAddressId() != null) {
            mono = mono.zipWith(addressRepository.findById(clientEntity.getAddressId()))
                    .map(result -> result.getT1().setAddress(result.getT2()));
        }

        return mono;
    }

    public Mono<ClientEntity> update(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    public Mono<Void> delete(ClientEntity clientEntity) {
        return clientRepository.deleteClientAndContactById(clientEntity.getId());
    }

    public Mono<ClientEntity> findById(long id) {
        return clientRepository.findById(id)
                .flatMap(this::loadRelations);
    }


}
