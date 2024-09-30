package org.webflux.microservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.BaseRepositoryTest;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.model.ContactEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ClientServiceTest extends BaseRepositoryTest {

    private final ClientService clientService;

    @Autowired
    public ClientServiceTest(ClientService clientService) {
        this.clientService = clientService;
    }

    @Test
    void create() {
        // Given
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setGivenName("Jimmy");
        clientEntity.setMiddleInitial("E");
        clientEntity.setSurname("Carter");
        clientEntity.setCompany("Habitat For Humanity");
        clientEntity.setTitle("Spokesman");
        AddressEntity address = new AddressEntity();
        address.setAddressLine1("111 Peanut Lane");
        address.setCountry("USA");
        address.setPostalCode("31780");
        address.setCity("Plains");
        address.setState("GA");
        clientEntity.setAddress(address);
        ContactEntity contact = new ContactEntity();
        contact.setEmail("info@cartercenter.org");
        contact.setPhone("(404) 420-5100");
        contact.setFax("(404) 420-5109");
        clientEntity.setContactInfo(contact);

        // When
        Mono<ClientEntity> result = clientService.create(clientEntity);

        // Then
        StepVerifier.create(result)
                .assertNext(createdClient -> {
                    assertNotNull(createdClient.getId());
                    assertEquals("Jimmy", createdClient.getGivenName());
                    assertEquals("Carter", createdClient.getSurname());
                    assertNotNull(createdClient.getAddressId());
                    assertEquals(createdClient.getId(), createdClient.getContactInfo().getClientId());
                })
                .verifyComplete();
    }

@Test
void findAll() {
    // When
    Flux<ClientEntity> result = clientService.findAll();

    // Then
    StepVerifier.create(result.collectList())
            .assertNext(clients -> {
                assertNotNull(clients);
                assertTrue(clients.stream().anyMatch(client ->
                        "John".equals(client.getGivenName()) &&
                        "Doe".equals(client.getSurname()) &&
                        "446 Elm St".equals(client.getAddress().getAddressLine1()) &&
                        "Othertown".equals(client.getAddress().getCity()) &&
                        "CO".equals(client.getAddress().getState()) &&
                        "USA".equals(client.getAddress().getCountry()) &&
                        "data@datar.us".equals(client.getContactInfo().getEmail()) &&
                        "3037987766".equals(client.getContactInfo().getPhone()) &&
                        "7207986677".equals(client.getContactInfo().getFax())
                ));
                assertTrue(clients.stream().anyMatch(client ->
                        "Richard".equals(client.getGivenName()) &&
                        "Nixon".equals(client.getSurname()) &&
                        "336 Main St".equals(client.getAddress().getAddressLine1()) &&
                        "Anytown".equals(client.getAddress().getCity()) &&
                        "CO".equals(client.getAddress().getState()) &&
                        "USA".equals(client.getAddress().getCountry()) &&
                        "tricky@dick.us".equals(client.getContactInfo().getEmail()) &&
                        "5057987766".equals(client.getContactInfo().getPhone()) &&
                        "5057986677".equals(client.getContactInfo().getFax())
                ));
                assertTrue(clients.stream().anyMatch(client ->
                        "Gerald".equals(client.getGivenName()) &&
                        "Ford".equals(client.getSurname()) &&
                        "336 Main St".equals(client.getAddress().getAddressLine1()) &&
                        "Anytown".equals(client.getAddress().getCity()) &&
                        "CO".equals(client.getAddress().getState()) &&
                        "USA".equals(client.getAddress().getCountry()) &&
                        "ford@fpotus.us".equals(client.getContactInfo().getEmail()) &&
                        "5057987766".equals(client.getContactInfo().getPhone()) &&
                        "5057986677".equals(client.getContactInfo().getFax())
                ));
            })
            .verifyComplete();
    }
    @Test
    void update() {
        // Given
        long clientId = 6L; // Assuming the ID of the client to update is 6
        Mono<ClientEntity> existingClientMono = clientService.findById(clientId);

        StepVerifier.create(existingClientMono)
                .assertNext(existingClient -> {
                    assertNotNull(existingClient);

                    // Modify the client entity
                    existingClient.setGivenName("UpdatedName");
                    existingClient.setSurname("UpdatedSurname");

                    // When
                    Mono<ClientEntity> updatedClientMono = clientService.update(existingClient);

                    // Then
                    StepVerifier.create(updatedClientMono)
                            .assertNext(updatedClient -> {
                                assertNotNull(updatedClient);
                                assertEquals("UpdatedName", updatedClient.getGivenName());
                                assertEquals("UpdatedSurname", updatedClient.getSurname());
                            })
                            .verifyComplete();
                })
                .verifyComplete();
    }

    @Test
    void delete() {
        // Given
        long clientId = 5L; // Assuming there is a record with ID > 3

        // Retrieve the client to ensure it exists
        Mono<ClientEntity> clientToDelete = clientService.findById(clientId);

        StepVerifier.create(clientToDelete)
                .assertNext(client -> {
                    assertNotNull(client);

                    // When
                    Mono<Void> deleteResult = clientService.delete(client);

                    // Then
                    StepVerifier.create(deleteResult)
                            .verifyComplete();

                    // Verify the client is no longer in the database
                    Mono<ClientEntity> deletedClient = clientService.findById(clientId);
                    StepVerifier.create(deletedClient)
                            .expectNextCount(0)
                            .verifyComplete();
                })
                .verifyComplete();
    }

    @Test
    void findById() {
        // Given
        long clientId = 1L; // Assuming the ID of John Doe is 1

        // When
        Mono<ClientEntity> result = clientService.findById(clientId);

        // Then
        StepVerifier.create(result)
                .assertNext(client -> {
                    assertNotNull(client);
                    assertEquals("John", client.getGivenName());
                    assertEquals("Doe", client.getSurname());
                    assertEquals("446 Elm St", client.getAddress().getAddressLine1());
                    assertEquals("Othertown", client.getAddress().getCity());
                    assertEquals("CO", client.getAddress().getState());
                    assertEquals("USA", client.getAddress().getCountry());
                    assertEquals("data@datar.us", client.getContactInfo().getEmail());
                    assertEquals("3037987766", client.getContactInfo().getPhone());
                    assertEquals("7207986677", client.getContactInfo().getFax());
                })
                .verifyComplete();
    }
}