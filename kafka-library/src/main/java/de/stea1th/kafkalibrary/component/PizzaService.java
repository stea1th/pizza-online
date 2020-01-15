package de.stea1th.kafkalibrary.component;

import de.stea1th.kafkalibrary.dto.PizzaDto;

public interface PizzaService {

    void send(PizzaDto pizzaDto);

    String writeValueAsString(PizzaDto pizzaDto);

}
