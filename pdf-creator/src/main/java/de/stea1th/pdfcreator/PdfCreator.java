package de.stea1th.pdfcreator;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;


@Component
public class PdfCreator {


    @SneakyThrows
    public void createPdf() {
        try (PdfDocument pdf = new PdfDocument(new PdfWriter("pdf-creator\\src\\main\\resources\\pdf\\test.pdf", new WriterProperties().addXmpMetadata()));
             Document document = new Document(pdf, PageSize.A4)) {

            pdf.getCatalog().setLang(new PdfString("de-DE"));
            pdf.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));
//            PdfDocumentInfo info = pdf.getDocumentInfo();
//            info.setTitle("Test PDF!!!");
            Paragraph p = new Paragraph();
            p.setPaddingTop(120);
            p.setPaddingLeft(30);
            p.setFontSize(8);
            p.add(new Text("Ihr FirmenSignature GmbH / Musterstadt / 12345 Musterstadt"));

            List list = new List();
            list.add(new ListItem("Herr Vadim Pechionkin"));
            list.add(new ListItem("Musterstrasse 5"));
            list.add(new ListItem("12345 Musterstadt"));
            list.add(new ListItem("Deutschland"));
            list.setListSymbol("");
            list.setPaddingTop(10);
            list.setPaddingLeft(30);
            list.setFontSize(10);

            Table table1 = new Table(new float[]{4, 4, 4});
            table1.setWidth(UnitValue.createPercentValue(60));
            table1.setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.LEFT);
            table1.setPaddingRight(25);
            table1.setMarginTop(25);
            table1.setMarginBottom(25);
            table1.setFontSize(10);
            table1.addHeaderCell(new Cell().add(new Paragraph("Kundennummer")).setBorder(null).setBold());
            table1.addHeaderCell(new Cell().add(new Paragraph("Lieferdatum")).setBorder(null).setBold());
            table1.addHeaderCell(new Cell().add(new Paragraph("Rechnungsdatum")).setBorder(null).setBold());
            table1.addCell(new Cell().add(new Paragraph("YYYYYYYYY")).setBorder(null));
            table1.addCell(new Cell().add(new Paragraph("TT/MM/JJJJ")).setBorder(null));
            table1.addCell(new Cell().add(new Paragraph("TT/MM/JJJJ")).setBorder(null));

            Paragraph p1 = new Paragraph();
            p1.setPaddingLeft(30);
            p1.setFontSize(20);
            p1.add(new Text("Rechnung Nr. 12345"));

            Paragraph p2 = new Paragraph();
            p2.setPaddingLeft(30);
            p2.setPaddingRight(30);
            p2.setPaddingTop(15);
            p2.setFontSize(10);
            p2.add(new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut\n" +
                    "       labore et dolore magna aliqua Ut enim ad minim veniam. "));

            Table table2 = new Table(new float[]{2, 7, 3, 3, 3});
            table2.setWidth(UnitValue.createPercentValue(100));
            table2.setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.LEFT);
            table2.setMarginLeft(30);
            table2.setMarginRight(30);
            table2.setMarginTop(10);
            table2.setFontSize(10);
            table2.addHeaderCell(new Cell().add(new Paragraph("Pos.")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
            table2.addHeaderCell(new Cell().add(new Paragraph("Bezeichnung")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
            table2.addHeaderCell(new Cell().add(new Paragraph("Menge")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
            table2.addHeaderCell(new Cell().add(new Paragraph("Einzel")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
            table2.addHeaderCell(new Cell().add(new Paragraph("Gesamt")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));



            document.add(p);
            document.add(list);
            document.add(table1);
            document.add(p1);
            document.add(p2);
            document.add(table2);

        }
    }

//    public Rectangle createRectangle(Document document) {
//        Rectangle pageEffectiveArea = document.getPageEffectiveArea(PageSize.A4);
//        float
//
//    }

//


}
