package org.webflux.microservice.rest.api;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Client {

    private Long id;
    private String givenName;
    private String middleInitial;
    private String surname;
    private String title;
    private String company;

    private Address address;
    private Contact contactInfo;

}
