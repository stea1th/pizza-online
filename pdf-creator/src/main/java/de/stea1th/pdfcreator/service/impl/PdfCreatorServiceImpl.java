package de.stea1th.pdfcreator.service.impl;

import de.stea1th.commonslibrary.dto.PdfCreatorDto;
import de.stea1th.pdfcreator.component.InvoicePdfBlockCreator;
import de.stea1th.pdfcreator.service.PdfCreatorService;
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
