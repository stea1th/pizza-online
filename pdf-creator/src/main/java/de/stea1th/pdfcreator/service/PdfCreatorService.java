package de.stea1th.pdfcreator.service;

import de.stea1th.commonslibrary.dto.PdfCreatorDto;

public interface PdfCreatorService {

    void createInvoiceAsPdf(PdfCreatorDto pdfCreatorDto);
}
