package de.stea1th.web.service;

import de.stea1th.kafkalibrary.component.KafkaProducer;
import de.stea1th.kafkalibrary.dto.ProductDto;
import de.stea1th.web.kafka.ProductKafkaConsumer;
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

    private final static Integer ATTEMPT = 5;
    private final static Integer SLEEP_DURATION = 100;

    @Value("${product.get.all}")
    private String productsGetAllTopic;

    public ProductServiceImpl(KafkaProducer kafkaProducer, ProductKafkaConsumer productKafkaConsumer) {
        this.kafkaProducer = kafkaProducer;
        this.productKafkaConsumer = productKafkaConsumer;
    }

    @Override
    @SneakyThrows
    public List<ProductDto> getAll() {
        kafkaProducer.produce(productsGetAllTopic, "pizza-online", "");
        List<ProductDto> productDtoList = null;
        for (int i = 0; i <ATTEMPT; i++) {
            TimeUnit.MILLISECONDS.sleep(SLEEP_DURATION);
            productDtoList = productKafkaConsumer.getProductDtoList();
            if(productDtoList != null) break;
        }
        return productDtoList;
    }

    @Override
    public ProductDto get(int productId) {
        return null;
    }
}
