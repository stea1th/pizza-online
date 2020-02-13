package de.stea1th.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonlibrary.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductKafkaConsumerImpl implements ProductKafkaConsumer {

    private List<ProductDto> productDtoList;

    private ProductDto productDto;

    public List<ProductDto> getProductDtoList() {
        List<ProductDto> tempProducts = productDtoList;
        productDtoList = null;
        return tempProducts;
    }

    @KafkaListener(topics = "${product.receive.product.all}", groupId = "pizza-online")
    public void processGetAllProducts(String products) {
        log.info("received products = {}", products);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            productDtoList = objectMapper.readValue(products, new TypeReference<List<ProductDto>>(){});
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
