package org.webflux.microservice;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.context.ActiveProfiles;
import org.webflux.microservice.config.TestDataConfig;
import org.webflux.microservice.model.AddressEntity;
import org.webflux.microservice.model.ClientEntity;
import org.webflux.microservice.model.ContactEntity;
import org.webflux.microservice.repository.AddressRepository;
import org.webflux.microservice.repository.ClientRepository;
import org.webflux.microservice.repository.ContactRepository;
import reactor.core.publisher.Flux;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestDataConfig.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseRepositoryTest {

    @Autowired
    protected DatabaseClient databaseClient;

    @Autowired
    protected TestDataConfig testDataConfig;

    @Autowired
    protected AddressRepository addressRepository;

    @Autowired
    protected ClientRepository clientRepository;

    @Autowired
    protected ContactRepository contactRepository;

    private static boolean isSetUpDone = false;

    @BeforeAll
    void setUp() {
        synchronized (BaseRepositoryTest.class) {
            if (!isSetUpDone) {
                testDataConfig.initializeTestData(databaseClient).block();

                // Step 1: Read and save address records
                Flux<AddressEntity> addresses = Flux.fromStream(new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/AddressData/addressdata.csv"))))
                        .lines()
                        .skip(1) // Skip header line
                        .map(line -> {
                            String[] fields = line.split(",");
                            AddressEntity addressEntity = new AddressEntity();
                            addressEntity.setPostalCode(fields.length > 0 ? fields[0] : null);
                            addressEntity.setCountry(fields.length > 1 ? fields[1] : null);
                            addressEntity.setAddressLine1(fields.length > 2 ? fields[2] : null);
                            addressEntity.setAddressLine2(fields.length > 3 ? fields[3] : null);
                            addressEntity.setCity(fields.length > 4 ? fields[4] : null);
                            addressEntity.setState(fields.length > 5 ? fields[5] : null);
                            return addressEntity;
                        }));

                List<Long> addressIdList = new ArrayList<>();
                addressRepository.saveAll(addresses)
                        .doOnNext(address -> addressIdList.add(address.getId()))
                        .blockLast();
                AtomicInteger addressIndex = new AtomicInteger(0);

                // Step 2: Read client data and map address IDs
                Flux<ClientEntity> clients = Flux.fromStream(new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/ClientData/clientdata.csv"))))
                        .lines()
                        .skip(1) // Skip header line
                        .map(line -> {
                            String[] fields = line.split(",");
                            ClientEntity clientEntity = new ClientEntity();
                            clientEntity.setGivenName(fields.length > 0 ? fields[0] : null);
                            clientEntity.setMiddleInitial(fields.length > 1 ? (fields[1].length() > 0 ? fields[1].substring(0, 1) : null) : null);
                            clientEntity.setSurname(fields.length > 2 ? fields[2] : null);
                            clientEntity.setTitle(fields.length > 3 ? fields[3] : null);
                            clientEntity.setCompany(fields.length > 4 ? fields[4] : null);
                            clientEntity.setAddressId(addressIdList.get(addressIndex.getAndIncrement() % addressIdList.size()));
                            return clientEntity;
                        }));

                AtomicInteger clientIndex = new AtomicInteger(0);
                List<Long> clientIdList = new ArrayList<>();
                clientRepository.saveAll(clients)
                        .doOnNext(client -> clientIdList.add(client.getId()))
                        .blockLast();

                // Step 3: Read contact data and map client IDs
                Flux<ContactEntity> contacts = Flux.fromStream(new BufferedReader(new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/ContactData/contactdata.csv"))))
                        .lines()
                        .skip(1) // Skip header line
                        .map(line -> {
                            String[] fields = line.split(",");
                            ContactEntity contactEntity = new ContactEntity();
                            contactEntity.setEmail(fields.length > 0 ? fields[0] : null);
                            contactEntity.setPhone(fields.length > 1 ? fields[1] : null);
                            contactEntity.setMobile(fields.length > 2 ? fields[2] : null);
                            contactEntity.setFax(fields.length > 3 ? fields[3] : null);
                            contactEntity.setClientId(clientIdList.get(clientIndex.getAndIncrement() % clientIdList.size()));
                            return contactEntity;
                        }));

                // Step 4: Save contact records using custom insert method
                contacts.flatMap(contactRepository::insert).blockLast();

                isSetUpDone = true;
            }
        }
    }
}