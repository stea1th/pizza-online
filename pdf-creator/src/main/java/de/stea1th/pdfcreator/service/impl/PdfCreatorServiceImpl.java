package de.stea1th.pdfcreator.service.impl;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import de.stea1th.commonslibrary.dto.PdfCreatorDto;
import de.stea1th.pdfcreator.creator.InvoicePdfBlockCreator;
import de.stea1th.pdfcreator.service.PdfCreatorService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;


@Service
public class PdfCreatorServiceImpl implements PdfCreatorService {

    private InvoicePdfBlockCreator pdfBlockCreator;

    public PdfCreatorServiceImpl(InvoicePdfBlockCreator pdfBlockCreator) {
        this.pdfBlockCreator = pdfBlockCreator;
    }

    @Override
    public void createInvoiceAsPdf(PdfCreatorDto pdfCreatorDto) {
        pdfBlockCreator.createPdf(pdfCreatorDto);
    }


}
