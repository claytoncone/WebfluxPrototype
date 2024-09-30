package org.webflux.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.BaseRepositoryTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ContactRepositoryTest extends BaseRepositoryTest {

    @Test
    void testFindContactByPhone() {
        StepVerifier.create(contactRepository.findContactByPhone("5057987766"))
                .expectNextMatches(contact -> {
                    assertNotNull(contact);
                    assertEquals("5057987766", contact.getPhone());
                    return true;
                })
                .expectNextMatches(contact -> {
                    assertNotNull(contact);
                    assertEquals("5057987766", contact.getPhone());
                    return true;
                })
                .verifyComplete();
    }
}