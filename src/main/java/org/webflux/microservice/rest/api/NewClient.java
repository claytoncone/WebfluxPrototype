package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class NewClient {
    @NotBlank
    @Size(max = 255)
    private String givenName;
    @Size(max = 1)
    private String middleInitial;
    @NotBlank
    @Size(max = 255)
    private String surname;
    @Size(max = 255)
    private String title;
    @Size(max = 255)
    private String company;

    private Address address;
    private Contact contactInfo;
}
