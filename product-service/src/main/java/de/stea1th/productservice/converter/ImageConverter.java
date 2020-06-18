package de.stea1th.productservice.converter;


import de.stea1th.productservice.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Component
@Slf4j
public class ImageConverter {

    private String encodeFileFromResourcesToBase64(String fileName) throws IOException {
        log.info("Trying to encode image file: {}", fileName);
        File file = ResourceUtils.getFile("classpath:pic/" + fileName);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }

    public String convertImageToBase64(String imageName) {
        log.info("Converting image: {} ", imageName);
        String base64 = imageName;
        try {
            base64 = encodeFileFromResourcesToBase64(imageName);
        } catch (IOException e) {
            log.info("No picture for product with name {} available", imageName);
        }
        return base64;
    }
}
