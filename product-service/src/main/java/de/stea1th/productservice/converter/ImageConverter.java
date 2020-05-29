package de.stea1th.productservice.converter;


import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

@Component
public class ImageConverter {

    public String encodeFileFromResourcesToBase64(String fileName) throws IOException {
        File file = ResourceUtils.getFile("classpath:pic/" + fileName);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(fileContent);
    }
}
