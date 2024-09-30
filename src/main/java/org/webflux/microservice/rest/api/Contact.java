package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Contact {
    @NotNull
    private Long clientId;
    @NotBlank
    @Email
    private String email;
    private String phone;
    private String mobile;
    private String fax;
}
