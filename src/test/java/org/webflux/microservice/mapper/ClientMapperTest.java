package org.webflux.microservice.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.model.ContactEntity;
import org.webflux.microservice.rest.api.Client;
import org.webflux.microservice.rest.api.NewClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientMapperTest {

    private final ClientMapper mapper;

    @Autowired
    ClientMapperTest(ClientMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void toDTO() {
        // given
        ClientEntity clientEntity = new ClientEntity()
                .setId(1L)
                .setGivenName("John")
                .setSurname("Doe")
                .setAddress(new AddressEntity()
                        .setId(1L)
                        .setCountry("USA")
                        .setPostalCode("12345")
                        .setAddressLine1("123 Main St")
                        .setAddressLine2("Apt 101")
                        .setCity("Anytown")
                        .setState("CO")
                );
        clientEntity.setContactInfo(new ContactEntity()
                        .setClientId(clientEntity.getId())
                        .setEmail("j6GK4@example.com")
                        .setPhone("123-456-7890")
                        .setMobile("123-456-7890")
                        .setFax("987-654-3210")
                );



        // when
        Client resource = mapper.toDTO(clientEntity);

        // then
        assertNotNull(resource);
        assertEquals(clientEntity.getId(), resource.getId());
        assertEquals(clientEntity.getGivenName(), resource.getGivenName());
        assertEquals(clientEntity.getSurname(), resource.getSurname());
        assertNotNull(resource.getAddress());
        assertEquals(clientEntity.getAddress().getId(), resource.getAddress().getId());
        assertEquals(clientEntity.getAddress().getCountry(), resource.getAddress().getCountry());
        assertEquals(clientEntity.getAddress().getPostalCode(), resource.getAddress().getPostalCode());
        assertEquals(clientEntity.getAddress().getAddressLine1(), resource.getAddress().getAddressLine1());
        assertEquals(clientEntity.getAddress().getAddressLine2(), resource.getAddress().getAddressLine2());
        assertEquals(clientEntity.getAddress().getCity(), resource.getAddress().getCity());
        assertEquals(clientEntity.getAddress().getState(), resource.getAddress().getState());
        assertNotNull(resource.getContactInfo());
        assertEquals(clientEntity.getContactInfo().getPhone(), resource.getContactInfo().getPhone());
        assertEquals(clientEntity.getContactInfo().getFax(), resource.getContactInfo().getFax());
        assertEquals(clientEntity.getContactInfo().getMobile(), resource.getContactInfo().getMobile());
        assertEquals(clientEntity.getContactInfo().getEmail(), resource.getContactInfo().getEmail());
    }

    @Test
    void toModel() {
        // given

        NewClient resource = new NewClient();
        resource.setGivenName("John");
        resource.setSurname("Doe");
        resource.setTitle("Little Boss");
        resource.setCompany("Army Surplus");
        resource.setAddressId(1L);

        ClientEntity clientEntity = mapper.toModel(resource);

        assertNotNull(clientEntity);
        assertEquals(resource.getGivenName(), clientEntity.getGivenName());
        assertNull(clientEntity.getMiddleInitial());
        assertEquals(resource.getSurname(), clientEntity.getSurname());
        assertEquals(resource.getTitle(), clientEntity.getTitle());
        assertEquals(resource.getCompany(), clientEntity.getCompany());
        assertEquals(resource.getAddressId(), clientEntity.getAddressId());
    }
}