package de.stea1th.web.kafka;

import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ProductCostKafkaConsumerImpl implements ProductCostKafkaConsumer {


    private List<ProductCostInCartDto> productCostInCartDtoList;

    @Override
    public List<ProductCostInCartDto> getProductCostInCartDtoList() {
        return productCostInCartDtoList;
    }

    @KafkaListener(topics = "${product-cost.receive.cart}", groupId = "pizza-online")
    public void processGetCartProductCosts(String products) {
        log.info("received product-costs in cart = {}", products);
    }

}
