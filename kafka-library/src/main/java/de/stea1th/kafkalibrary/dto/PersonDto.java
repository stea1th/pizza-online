package de.stea1th.kafkalibrary.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class PersonDto implements Serializable {

    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

//    private List<OrderDto> orders;

}
