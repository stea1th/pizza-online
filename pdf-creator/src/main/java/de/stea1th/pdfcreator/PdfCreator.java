package de.stea1th.pdfcreator;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;


@Component
public class PdfCreator {


    @SneakyThrows
    public void createPdf() {
        try (PdfDocument pdf = new PdfDocument(new PdfWriter("pdf-creator\\src\\main\\resources\\pdf\\test.pdf", new WriterProperties().addXmpMetadata()));
             Document document = new Document(pdf)) {
//        PdfDocument pdf = new PdfDocument(new PdfWriter("classpath:pdf/test.pdf", new WriterProperties().addXmpMetadata()));

//        Document document = new Document(pdf);
        pdf.getCatalog().setLang(new PdfString("de-DE"));
        pdf.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
        PdfDocumentInfo info = pdf.getDocumentInfo();
        info.setTitle("Test PDF!!!");

        Paragraph p = new Paragraph();
        p.add(new Text("New Pdf File..."));
        document.add(p);
//        "pdf-creator\\src\\pdf\\test.pdf"

//        PdfWriter writer = new PdfWriter("pdf-creator\\src\\main\\resources\\pdf\\test.pdf", new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        pdfDocument.setTagged();
//        Document document = new Document(pdfDocument);
//        document.add(new Paragraph("Hello world!"));
//        document.close();
        }
    }

//


}
