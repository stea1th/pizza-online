package de.stealth.personservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Address extends AbstractBaseEntity {

    private String street;
    private String zip;
    private String city;
    private String country;
}
