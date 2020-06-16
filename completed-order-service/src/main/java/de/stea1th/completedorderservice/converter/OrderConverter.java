package de.stea1th.completedorderservice.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.completedorderservice.dto.OrderDto;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class OrderConverter {

    private final ObjectMapper objectMapper;

    public OrderConverter() {
        this.objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    @SneakyThrows
    public OrderDto convertJsonToDto(String json) {
        log.info("Converting json: {}", json);
        JsonNode rootNode = objectMapper.readTree(json);
        return convertNodeToDto(rootNode);

    }

    public OrderDto convertNodeToDto(JsonNode rootNode) {
        log.info("Converting node: {}", rootNode);
        var dto = new OrderDto();
        dto.setId(rootNode.get("id").asInt());
        dto.setCreated(parseToLocalDateTime(rootNode.get("created").asText()));
        dto.setCompleted(parseToLocalDateTime(rootNode.get("completed").asText()));
        return dto;
    }

    @SneakyThrows
    public List<OrderDto> convertToDtoList(String json) {
        log.info("Converting to list: {}", json);
        JsonNode rootNode = objectMapper.readTree(json);
        List<OrderDto> list = new ArrayList<>();
        Iterator<JsonNode> elements = rootNode.elements();
        elements.forEachRemaining(el -> list.add(convertNodeToDto(el)));
        return list;

    }

    private LocalDateTime parseToLocalDateTime(String message) {
        return !message.equals("null") ? LocalDateTime.parse(message.replace(" ", "T")) : null;
    }
}
