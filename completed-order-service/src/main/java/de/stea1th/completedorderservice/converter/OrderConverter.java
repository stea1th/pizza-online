package de.stea1th.completedorderservice.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import de.stea1th.completedorderservice.dto.OrderDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class OrderConverter {

    //    private LocalDateTimeConverter localDateTimeConverter;
    private final ObjectMapper objectMapper;

    public OrderConverter() {
        this.objectMapper = new ObjectMapper();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME));
//        objectMapper.registerModule(javaTimeModule);
//        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        objectMapper.registerModule(new LocalDateTimeDeserializer());
    }

    @SneakyThrows
    public OrderDto convertToDto(String json) {
        log.info("receiving json: {}", json);
        JsonNode rootNode = objectMapper.readTree(json);
        var dto = new OrderDto();
        dto.setId(rootNode.get("id").asInt());
        dto.setCreated(parseToLocalDateTime(rootNode.get("created").asText()));
        dto.setCompleted(parseToLocalDateTime(rootNode.get("completed").asText()));
        return dto;
    }

    private LocalDateTime parseToLocalDateTime(String message) {
        return !message.equals("null") ? LocalDateTime.parse(message.replace(" ", "T")) : null;
    }
}
