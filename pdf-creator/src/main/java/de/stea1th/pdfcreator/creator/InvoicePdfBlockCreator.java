package de.stea1th.pdfcreator.creator;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.ColumnDocumentRenderer;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import org.springframework.stereotype.Component;

@Component
public class InvoicePdfBlockCreator {

    public Paragraph createHeaderCompanySignature() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingTop(120);
        paragraph.setPaddingLeft(30);
        paragraph.setFontSize(8);
        paragraph.add(new Text("Ihr FirmenSignature GmbH / Musterstadt / 12345 Musterstadt"));
        return paragraph;
    }

    public List createCustomerDetails() {
        List list = new List();
        list.add(new ListItem("Herr Vadim Pechionkin"));
        list.add(new ListItem("Musterstrasse 5"));
        list.add(new ListItem("12345 Musterstadt"));
        list.add(new ListItem("Deutschland"));
        list.setListSymbol("");
        list.setPaddingTop(10);
        list.setPaddingLeft(30);
        list.setFontSize(10);
        return list;
    }

    public Table createInvoiceDetailTable() {
        Table table = new Table(new float[]{4, 4, 4});
        table.setWidth(UnitValue.createPercentValue(60));
        table.setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.LEFT);
        table.setPaddingRight(25);
        table.setMarginTop(25);
        table.setMarginBottom(25);
        table.setFontSize(10);
        table.addHeaderCell(new Cell().add(new Paragraph("Kundennummer")).setBorder(null).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Lieferdatum")).setBorder(null).setBold());
        table.addHeaderCell(new Cell().add(new Paragraph("Rechnungsdatum")).setBorder(null).setBold());
        table.addCell(new Cell().add(new Paragraph("YYYYYYYYY")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("TT/MM/JJJJ")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("TT/MM/JJJJ")).setBorder(null));
        return table;
    }

    public Paragraph createInvoiceNumber() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingLeft(30);
        paragraph.setFontSize(20);
        paragraph.add(new Text("Rechnung Nr. 12345"));
        return paragraph;
    }

    public Paragraph createForeword() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingLeft(30);
        paragraph.setPaddingRight(30);
        paragraph.setPaddingTop(15);
        paragraph.setFontSize(10);
        paragraph.add(new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut\n" +
                "       labore et dolore magna aliqua Ut enim ad minim veniam. "));
        return paragraph;
    }

    public Table createInvoiceProductTable() {
        Table table = new Table(new float[]{2, 7, 3, 3, 3});
        table.setWidth(UnitValue.createPercentValue(100));
        table.setHorizontalAlignment(HorizontalAlignment.CENTER).setTextAlignment(TextAlignment.LEFT);
        table.setMarginLeft(30);
        table.setMarginRight(30);
        table.setMarginTop(10);
        table.setMarginBottom(5);
        table.setFontSize(10);

        table.addHeaderCell(new Cell().add(new Paragraph("Pos.")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
        table.addHeaderCell(new Cell().add(new Paragraph("Bezeichnung")).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
        table.addHeaderCell(new Cell().add(new Paragraph("Menge")).setTextAlignment(TextAlignment.CENTER).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
        table.addHeaderCell(new Cell().add(new Paragraph("Einzel")).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
        table.addHeaderCell(new Cell().add(new Paragraph("Gesamt")).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBold().setBorderTop(new SolidBorder(1)).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("1")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("Pizza Tonne, big size")).setBorder(null).setBold());
        table.addCell(new Cell().add(new Paragraph("2 Stück")).setTextAlignment(TextAlignment.CENTER).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("8,25")).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("16,50")).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("2")).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("Pizza Salami, big size")).setBorder(null).setBold());
        table.addCell(new Cell().add(new Paragraph("1 Stück")).setTextAlignment(TextAlignment.CENTER).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("5,00")).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        table.addCell(new Cell().add(new Paragraph("5,00")).setTextAlignment(TextAlignment.RIGHT).setBorder(null));
        return table;
    }

    public Table createInvoiceTotalTable() {
        Table table = new Table(new float[]{7, 3});
        table.setWidth(UnitValue.createPercentValue(40));
        table.setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.RIGHT);
        table.setMarginRight(30);
        table.setFontSize(10);
//            table3.setBorder(Border.NO_BORDER);
        table.addCell(new Cell().add(new Paragraph("Summe Netto")).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("21,50")).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("USt 7,00%")).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("1,50")).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("Betrag")).setBorder(null).setBold().setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("23,00")).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBold().setBorderBottom(new SolidBorder(1)));
        return table;
    }

    public Paragraph createEpilogue() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingLeft(30);
        paragraph.setPaddingRight(30);
        paragraph.setPaddingTop(15);
        paragraph.setFontSize(10);
        paragraph.add(new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut\n" +
                "       labore et dolore magna aliqua Ut enim ad minim veniam. \n\n" +
                "Mit freundlichen Grüßen\n" +
                "Mathilda Musterfrau"));
        return paragraph;
    }

    public void createFooter(PdfDocument pdfDocument) {
        PdfCanvas pdfCanvas = new PdfCanvas(pdfDocument, 1);
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut" +
                "       labore et dolore magna aliqua Ut enim ad minim veniam.\n"));

        paragraph.setFontSize(10);
        paragraph.setPaddingLeft(30);
        paragraph.setBorderBottom(new SolidBorder(1));
        paragraph.setBorderTop(new SolidBorder(1));
        paragraph.setPaddingBottom(10);
        paragraph.setPaddingTop(10);



        float columnHeight = 30 * 2.5f;
        float x = 30 * 2;
        float columnWidth = (PageSize.A4.getWidth() - x * 2);
//        float y = (PageSize.A4.getHeight() - (30 + columnHeight));
        float y = 30;

        new Canvas(pdfCanvas, pdfDocument, new Rectangle(x, y, columnWidth, columnHeight)).add(paragraph);
//        pdfCanvas.rectangle(x, y, columnWidth, columnHeight);
//        pdfCanvas.stroke();
    }

}
