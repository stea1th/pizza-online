package de.stea1th.persist.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
@ToString(exclude = {"orderProductCosts"})
public class Order extends AbstractBaseEntity {

    private LocalDateTime created = LocalDateTime.now();

    private LocalDateTime completed;

    @JsonManagedReference
    @OneToMany(mappedBy = "order")
    private List<OrderProductCost> orderProductCosts;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    private Person person;
}
