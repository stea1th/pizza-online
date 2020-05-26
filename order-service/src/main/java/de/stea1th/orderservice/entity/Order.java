package de.stea1th.orderservice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "orders")
@ToString()
public class Order extends AbstractBaseEntity {

    private LocalDateTime created = LocalDateTime.now();

    @Column(name="completed")
    private LocalDateTime completed;

    @Column(columnDefinition = "person_id")
    private Integer personId;
}
