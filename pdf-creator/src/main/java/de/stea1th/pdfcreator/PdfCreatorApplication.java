package de.stea1th.pdfcreator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PdfCreatorApplication {

    public static void main(String[] args) {
//        SpringApplication.run(PdfCreatorApplication.class, args);
        ApplicationContext app = SpringApplication.run(PdfCreatorApplication.class, args);
        PdfCreator creator = app.getBean(PdfCreator.class);
        creator.createPdf();
    }

}
