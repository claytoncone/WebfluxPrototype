package org.webflux.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "CLIENT")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ClientEntity {
    @Id
    private Long id;

    @Column("given_name")
    private String givenName;

    @Column("middle_initial")
    private String middleInitial;

    @Column("surname")
    private String surname;

    @Column("title")
    private String title;

    @Column("company")
    private String company;

    @Column("address_id")
    private Long addressId;

    @Transient
    private AddressEntity address;

    @Transient
    private ContactEntity contactInfo;

    @Version
    private Long version;

    @CreatedDate
    @Column("created_date")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column("last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Override
    public String toString() {
        return String.format("Client id=%d\nversion=%d\nName=%s %s %s\n title=%s, company%s", id,
                version, givenName, middleInitial, surname, title, company);
    }
}