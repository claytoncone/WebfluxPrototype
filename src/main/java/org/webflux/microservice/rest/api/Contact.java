package org.webflux.microservice.rest.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Contact {
    public Contact(String email) {
        this.email = email;
    }
    @NotBlank
    @Email
    private String email;
    private String phone;
    private String mobile;
    private String fax;
}
