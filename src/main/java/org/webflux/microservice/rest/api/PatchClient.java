package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Optional;

@Data
public class PatchClient {

    private Optional<@NotBlank @Size(max = 255)String> givenName;
    private Optional<@NotBlank @Size(max = 1) String> middleInitial;
    private Optional<@NotBlank @Size(max = 255) String> surname;
    private Optional<@NotBlank @Size(max = 255) String> title;
    private Optional<@NotBlank String> company;
    private Optional<Long> addressId;

}
