package org.webflux.microservice.rest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.data.r2dbc.mapping.R2dbcMappingContext;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.webflux.microservice.config.SecurityConfig;
import org.webflux.microservice.mapper.ClientMapper;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.repository.*;
import org.webflux.microservice.rest.api.Contact;
import org.webflux.microservice.rest.api.NewClient;
import org.webflux.microservice.service.ClientService;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

@WebFluxTest(ClientController.class)
@Import({SecurityConfig.class, ClientController.class})
@ContextConfiguration(classes = {ClientControllerTest.TestConfig.class})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ClientMapper clientMapper;

    @MockBean
    private ClientRepository clientRepository;

    @MockBean
    private AddressRepository addressRepository;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private CustomClientRepositoryImpl customClientRepositoryImpl;

    @MockBean
    private AddressRepositoryCustomImpl addressRepositoryCustomImpl;

    @MockBean
    private CustomContactRepositoryImpl customContactRepositoryImpl;

    private NewClient newClient;

    @BeforeEach
    void setUp() {
        newClient = new NewClient();
        newClient.setGivenName("Joe");
        newClient.setSurname("Blow");
        newClient.setCompany("We Buy Big Rigs");
        newClient.setContactInfo(new Contact("info@nmrigs.com"));

        Mockito.when(clientMapper.toModel(any(NewClient.class))).thenReturn(new ClientEntity());
        Mockito.when(clientService.create(any(ClientEntity.class))).thenReturn(Mono.empty());
    }

    @Test
    void addClient() {
        webTestClient.post()
                .uri("/rest/client")
                .bodyValue(newClient)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @Configuration
    static class TestConfig {
        @Bean
        @Primary
        public R2dbcMappingContext r2dbcMappingContext() {
            return new R2dbcMappingContext();
        }
    }
}