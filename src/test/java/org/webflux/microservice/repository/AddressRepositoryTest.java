package org.webflux.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.BaseRepositoryTest;
import org.webflux.microservice.model.AddressEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class AddressRepositoryTest extends BaseRepositoryTest {

    @Test
    void findAddressesByPostalCode() {
        Flux<AddressEntity> addresses = addressRepository.findAddressesByPostalCode("12345");
        AtomicInteger count = new AtomicInteger();

        StepVerifier.create(addresses.doOnNext(address -> count.incrementAndGet()))
                .recordWith(ArrayList::new)
                .thenConsumeWhile(address -> "12345".equals(address.getPostalCode()))
                .expectRecordedMatches(list -> list.stream().allMatch(address -> "12345".equals(address.getPostalCode())))
                .verifyComplete();

        assertEquals(4, count.get(), "The count should be 4"); // likely to change
    }

    @Test
    void findAddressesByState() {
        Flux<AddressEntity> addresses = addressRepository.findAddressesByState("CO");
        AtomicInteger count = new AtomicInteger();

        StepVerifier.create(addresses.doOnNext(address -> count.incrementAndGet()))
                .recordWith(ArrayList::new)
                .thenConsumeWhile(address -> "CO".equals(address.getState()))
                .expectRecordedMatches(list -> list.stream().allMatch(address -> "CO".equals(address.getState())))
                .verifyComplete();

        assertEquals(4, count.get(), "The count should be 4"); // likely to change
    }

    @Test
    void saveOrReturnDuplicate() {
        // given
        AddressEntity initialAddressEntity = new AddressEntity();
        initialAddressEntity.setAddressLine1("662 Main St");
        initialAddressEntity.setCity("Someplace");
        initialAddressEntity.setState("CA");
        initialAddressEntity.setPostalCode("54321");
        initialAddressEntity.setCountry("USA");

        // when
        Mono<AddressEntity> savedAddress = addressRepository.saveOrReturnDuplicate(initialAddressEntity).cache();
        Mono<AddressEntity> duplicate = addressRepository.saveOrReturnDuplicate(initialAddressEntity);

        // then
        StepVerifier.create(savedAddress)
                .assertNext(address -> {
                    assertNotNull(address.getId(), "ID should not be null");
                    assertNotNull(address.getCreatedDate(), "Created date should not be null");
                    assertNotNull(address.getLastModifiedDate(), "Last modified date should not be null");
                    assertEquals(0L, address.getVersion(), "Version should be 0");

                    assertEquals(initialAddressEntity.getAddressLine1(), address.getAddressLine1());
                    assertEquals(initialAddressEntity.getCity(), address.getCity());
                    assertEquals(initialAddressEntity.getState(), address.getState());
                    assertEquals(initialAddressEntity.getPostalCode(), address.getPostalCode());
                    assertEquals(initialAddressEntity.getCountry(), address.getCountry());
                })
                .verifyComplete();

        StepVerifier.create(savedAddress.zipWith(duplicate))
                .assertNext(tuple -> {
                    AddressEntity saved = tuple.getT1();
                    AddressEntity dup = tuple.getT2();

                    assertEquals(saved.getId(), dup.getId());
                    assertEquals(saved.getCreatedDate(), dup.getCreatedDate());
                    assertEquals(saved.getLastModifiedDate(), dup.getLastModifiedDate());
                    assertEquals(saved.getVersion(), dup.getVersion());
                    assertEquals(saved.getAddressLine1(), dup.getAddressLine1());
                    assertEquals(saved.getCity(), dup.getCity());
                    assertEquals(saved.getState(), dup.getState());
                    assertEquals(saved.getPostalCode(), dup.getPostalCode());
                    assertEquals(saved.getCountry(), dup.getCountry());
                })
                .verifyComplete();
    }

}