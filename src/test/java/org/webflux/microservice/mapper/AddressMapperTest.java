package org.webflux.microservice.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.rest.api.Address;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AddressMapperTest {

    private final AddressMapper mapper;

    @Autowired
    public AddressMapperTest(AddressMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void testMapAddressToDTO() {
    // given
        AddressEntity addressEntity = new AddressEntity()
            .setId(1L)
            .setCountry("USA")
            .setPostalCode("12345")
            .setAddressLine1("123 Main St")
            .setAddressLine2("Apt 101")
            .setCity("Anytown")
            .setState("CO")
            .setCreatedDate(LocalDateTime.now())
            .setLastModifiedDate(LocalDateTime.now())
            .setVersion(0L);


        // when
        Address resource = mapper.toDTO(addressEntity);

        // then
        assertNotNull(resource);
        assertEquals(addressEntity.getId(), resource.getId());
        assertEquals(addressEntity.getCountry(), resource.getCountry());
        assertEquals(addressEntity.getPostalCode(), resource.getPostalCode());
        assertEquals(addressEntity.getAddressLine1(), resource.getAddressLine1());
        assertEquals(addressEntity.getAddressLine2(), resource.getAddressLine2());
        assertEquals(addressEntity.getCity(), resource.getCity());
        assertEquals(addressEntity.getState(), resource.getState());
    }
}