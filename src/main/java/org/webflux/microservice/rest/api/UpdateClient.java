package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class UpdateClient {

    @NotBlank
    private String givenName;
    @Size(max = 1)
    private String middleInitial;
    @NotBlank
    private String surname;
    private String title;
    private String company;
    private Long addressId;
}
