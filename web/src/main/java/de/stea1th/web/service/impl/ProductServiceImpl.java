package de.stea1th.web.service.impl;

import de.stea1th.commonslibrary.component.KafkaProducer;
import de.stea1th.commonslibrary.dto.ProductDto;
import de.stea1th.web.kafka.ProductKafkaConsumer;
import de.stea1th.web.service.ProductService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private KafkaProducer kafkaProducer;

    private ProductKafkaConsumer productKafkaConsumer;

    @Value("#{new Integer('${kafka.service.attempt}')}")
    private Integer attempt;

    @Value("#{new Integer('${kafka.service.delay}')}")
    private Integer delay;

    @Value("${product.get.all}")
    private String productsGetAllTopic;

    @Value("${product.get.cart}")
    private String productsGetCartTopic;

    public ProductServiceImpl(KafkaProducer kafkaProducer, ProductKafkaConsumer productKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.productKafkaConsumer = productKafkaConsumer;
    }

    @SneakyThrows
    @Override
    public List<ProductDto> getAll() {
        kafkaProducer.produce(productsGetAllTopic, "pizza-online", "");
        List<ProductDto> productDtoList = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            productDtoList = productKafkaConsumer.getAllProducts();
            if (productDtoList != null) break;
        }
        return productDtoList;
    }

    @SneakyThrows
    @Override
    public List<ProductDto> getProductsInCart(String keycloak) {
        kafkaProducer.produce(productsGetCartTopic, "pizza-online", keycloak);
        List<ProductDto> productDtoList = null;
        for (int i = 0; i < attempt; i++) {
            TimeUnit.MILLISECONDS.sleep(delay);
            productDtoList = productKafkaConsumer.getProductsInCart();
            if (productDtoList != null) break;
        }
        return productDtoList;
    }

    @Override
    public ProductDto get(int productId) {
        return null;
    }
}
