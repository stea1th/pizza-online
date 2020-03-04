package de.stea1th.pdfcreator.component;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import de.stea1th.commonslibrary.dto.OrderDto;
import de.stea1th.commonslibrary.dto.PdfCreatorDto;
import de.stea1th.commonslibrary.dto.PersonDto;
import de.stea1th.commonslibrary.dto.ProductCostInCartDto;
import lombok.SneakyThrows;
import lombok.var;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InvoicePdfBlockCreator {

    @Value("${pdf.creator.base.path}")
    private String basePath;

    private PriceCalculator priceCalculator;

    public InvoicePdfBlockCreator(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    @SneakyThrows
    public void createPdf(PdfCreatorDto pdfCreatorDto) {
        try (PdfDocument pdf = new PdfDocument(new PdfWriter(basePath + "\\test.pdf", new WriterProperties().addXmpMetadata()));
             Document document = new Document(pdf, PageSize.A4)) {

            pdf.getCatalog().setLang(new PdfString("de-DE"));
            pdf.getCatalog().setViewerPreferences(new PdfViewerPreferences().setDisplayDocTitle(true));

            document.add(createHeaderCompanySignature());
            document.add(createCustomerDetails(pdfCreatorDto.getOrderDto().getPerson()));
            document.add(createInvoiceDetailTable(pdfCreatorDto.getOrderDto()));
            document.add(createInvoiceNumber(pdfCreatorDto.getOrderDto()));
            document.add(createForeword());
            document.add(createInvoiceProductTable(pdfCreatorDto.getProductCostInCartDtoList()));
            document.add(createInvoiceTotalTable(pdfCreatorDto.getProductCostInCartDtoList()));
            document.add(createEpilogue());
            createFooter(pdf);
        }
    }

    private Paragraph createHeaderCompanySignature() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingTop(120);
        paragraph.setPaddingLeft(30);
        paragraph.setFontSize(8);
        paragraph.add(new Text("Ihr FirmenSignature GmbH / Musterstadt / 12345 Musterstadt"));
        return paragraph;
    }

    private List createCustomerDetails(PersonDto personDto) {

        List list = new List();
        list.setListSymbol("");
        list.setPaddingTop(10);
        list.setPaddingLeft(30);
        list.setFontSize(10);

        list.add(new ListItem(String.format("Herr %s %s", personDto.getFirstName(), personDto.getLastName())));
        list.add(new ListItem(personDto.getAddress().getStreet()));
        list.add(new ListItem(String.format("%s %s", personDto.getAddress().getZip(), personDto.getAddress().getCity())));
        list.add(new ListItem(personDto.getAddress().getCountry()));

        return list;
    }

    private Table createInvoiceDetailTable(OrderDto orderDto) {
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
        table.addCell(new Cell().add(new Paragraph("BE-234-" + orderDto.getPerson().getId())).setBorder(null));
        table.addCell(new Cell().add(new Paragraph(LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))).setBorder(null));
        table.addCell(new Cell().add(new Paragraph(String.format("%s.%s.%d",
                prependZero(orderDto.getCompleted().getDayOfMonth()),
                prependZero(orderDto.getCompleted().getMonthValue()),
                orderDto.getCompleted().getYear()))).setBorder(null));
        return table;
    }

    private Paragraph createInvoiceNumber(OrderDto orderDto) {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingLeft(30);
        paragraph.setFontSize(20);
        paragraph.add(new Text("Rechnung Nr. " + orderDto.getId()));
        return paragraph;
    }

    private Paragraph createForeword() {
        Paragraph paragraph = new Paragraph();
        paragraph.setPaddingLeft(30);
        paragraph.setPaddingRight(30);
        paragraph.setPaddingTop(15);
        paragraph.setFontSize(10);
        paragraph.add(new Text("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut\n" +
                "       labore et dolore magna aliqua Ut enim ad minim veniam. "));
        return paragraph;
    }

    private Table createInvoiceProductTable(java.util.List<ProductCostInCartDto> dtoList) {
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
        for (int i = 0; i < dtoList.size(); i++) {
            var dto = dtoList.get(i);
            table.addCell(new Cell().add(new Paragraph(String.format("%d", i + 1))).setBorder(null));
            table.addCell(new Cell().add(new Paragraph(String.format("%s, %s", dto.getProduct().getName(), dto.getProperty()))).setBorder(null).setBold());
            table.addCell(new Cell().add(new Paragraph(String.format("%s Stück", dto.getQuantity())).setTextAlignment(TextAlignment.CENTER)).setBorder(null));
            table.addCell(new Cell().add(new Paragraph(String.format("%s", dto.getPrice())).setTextAlignment(TextAlignment.RIGHT)).setBorder(null));
            table.addCell(new Cell().add(new Paragraph(String.format("%s", (dto.getPrice().multiply(new BigDecimal(dto.getQuantity()))))).setTextAlignment(TextAlignment.RIGHT)).setBorder(null));
        }
        var discount = priceCalculator.sumDiscount(dtoList);
        if (discount.doubleValue() > 0) {
            table.addCell(new Cell().add(new Paragraph(String.format("%d", dtoList.size() + 1))).setBorder(null));
            table.addCell(new Cell().add(new Paragraph("Rabatt")).setBorder(null).setBold());
            table.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.CENTER)).setBorder(null));
            table.addCell(new Cell().add(new Paragraph("").setTextAlignment(TextAlignment.RIGHT)).setBorder(null));
            table.addCell(new Cell().add(new Paragraph(String.format("-%s", discount)).setTextAlignment(TextAlignment.RIGHT)).setBorder(null));
        }
        return table;
    }

    private Table createInvoiceTotalTable(java.util.List<ProductCostInCartDto> dtoList) {
        Table table = new Table(new float[]{7, 3});
        table.setWidth(UnitValue.createPercentValue(40));
        table.setHorizontalAlignment(HorizontalAlignment.RIGHT).setTextAlignment(TextAlignment.RIGHT);
        table.setMarginRight(30);
        table.setFontSize(10);

        var total = priceCalculator.sumTotal(dtoList).subtract(priceCalculator.sumDiscount(dtoList));
        var tax = priceCalculator.calculateTax(total);

        table.addCell(new Cell().add(new Paragraph("Summe Netto")).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph(total.subtract(tax).setScale(2, RoundingMode.CEILING).toString())).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("USt 7,00%")).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph(tax.toString())).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph("Betrag")).setBorder(null).setBold().setBorderBottom(new SolidBorder(1)));
        table.addCell(new Cell().add(new Paragraph(total.toString())).setTextAlignment(TextAlignment.RIGHT).setBorder(null).setBold().setBorderBottom(new SolidBorder(1)));
        return table;
    }

    private Paragraph createEpilogue() {
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

    private void createFooter(PdfDocument pdfDocument) {
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
        float y = 30;

        new Canvas(pdfCanvas, pdfDocument, new Rectangle(x, y, columnWidth, columnHeight)).add(paragraph);
    }

    private String prependZero(int number) {
        return number < 10 ? "0" + number : "" + number;
    }
}
