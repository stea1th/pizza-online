package de.stea1th.productservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(exclude = {"productCostList", "picture"})
@Entity
@Data
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "description"}, name = "product_unique_name_description_index")})
public class Product extends AbstractBaseEntity {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String picture;

    private boolean frozen;

    @JsonManagedReference
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductCost> productCostList;
}
