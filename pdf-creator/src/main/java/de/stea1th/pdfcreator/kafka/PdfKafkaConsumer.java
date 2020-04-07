package de.stea1th.pdfcreator.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.stea1th.commonslibrary.dto.CompletedOrderDto;
import de.stea1th.pdfcreator.service.PdfCreatorService;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PdfKafkaConsumer {

    private ObjectMapper objectMapper;

    private PdfCreatorService pdfCreatorService;

    public PdfKafkaConsumer(PdfCreatorService pdfCreatorService) {
        this.pdfCreatorService = pdfCreatorService;
        objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "${pdf-creator.create.invoice}", groupId = "pizza-online")
    public void processGetPdfCreator(String message) {
        log.info("received message = {}", message);
        try {
            var pdfCreator = objectMapper.readValue(message, CompletedOrderDto.class);
            pdfCreatorService.createInvoiceAsPdf(pdfCreator);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
    }
}
