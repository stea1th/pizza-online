package de.stea1th.pdfcreator.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfKafkaConsumer {


    @KafkaListener(topics = "${pdf-creator.create.invoice}", groupId = "pizza-online")
    public void processGetPdfCreator(String message) {
        log.info("received message = {}", message);
    }
}
