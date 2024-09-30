package org.webflux.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.BaseRepositoryTest;
import org.webflux.microservice.model.ContactEntity;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class ContactRepositoryTest extends BaseRepositoryTest {

    @Test
    void testFindContactByPhone() {
        Flux<ContactEntity> contactFlux = contactRepository.findContactByPhone("3037987766");

        StepVerifier.create(contactFlux)
                .expectNextMatches(contact -> {
                    assertNotNull(contact);
                    assertEquals("3037987766", contact.getPhone());
                    return true;
                })
                .expectComplete()
                .verify();
    }
}