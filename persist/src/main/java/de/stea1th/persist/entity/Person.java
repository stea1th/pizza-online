package de.stea1th.persist.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"keycloak"}, name = "person_unique_keycloak_index")})
public class Person extends AbstractBaseEntity {

    private String firstName;

    private String lastName;

    @Email
    private String email;

    @NotBlank
    private String keycloak;

//    @OneToMany(mappedBy = "person")
//    private List<Order> orders;
}
