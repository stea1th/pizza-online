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

@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"}, name = "person_unique_email_index")})
public class Person extends AbstractBaseEntity {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

//    @OneToMany(mappedBy = "person")
//    private List<Order> orders;
}
