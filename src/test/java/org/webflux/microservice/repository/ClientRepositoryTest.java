package org.webflux.microservice.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.BaseRepositoryTest;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ClientRepositoryTest extends BaseRepositoryTest {

    @Test
    void testFindClientByEmail() {
        //given contact data csv has stored data

        // then
        StepVerifier.create(clientRepository.findClientByEmail("jane.doe@bigrigs.com"))
                .expectNextMatches(client -> {
                    assertNotNull(client);
                    assertEquals("Jane", client.getGivenName());
                    return true;
                })
                .verifyComplete();

    }
}
