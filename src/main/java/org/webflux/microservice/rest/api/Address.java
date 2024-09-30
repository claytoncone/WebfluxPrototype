package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Address {
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String addressLine1;
    @Size(max = 255)
    private String addressLine2;
    @Size(max = 255)
    private String city;
    @Size(max = 255)
    private String state;
    @NotBlank
    @Size(max = 15)
    private String postalCode;
    @Size(max = 255)
    private String country;
}
