package org.webflux.microservice.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webflux.microservice.mapper.ClientMapper;
import org.webflux.microservice.rest.api.NewClient;
import org.webflux.microservice.service.ClientService;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/rest/client")
@RequiredArgsConstructor
@Slf4j
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @Operation(summary = "Add new client")
    @PostMapping
    public Mono<ResponseEntity<Void>> addClient(@Valid @RequestBody final NewClient newClient) {
        return clientService.create(clientMapper.toModel(newClient))
                .thenReturn(ResponseEntity.ok().build());
    }
}
