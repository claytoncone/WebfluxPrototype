package org.webflux.microservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "ADDRESS")
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    private Long id;
    private String country;
    @Column("zip")
    private String postalCode;
    @Column("address_line1")
    private String addressLine1;
    @Column("address_line2")
    private String addressLine2;
    private String city;
    private String state;

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
        return String.format("Address\n%d %d\n%s\n%s %s %s %s %s", id, version, addressLine1, addressLine2, city, state,
                postalCode, country);
    }
}