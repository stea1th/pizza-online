package de.stea1th.pdfcreator.service;

import de.stea1th.commonslibrary.dto.CompletedOrderDto;

public interface PdfCreatorService {

    void createInvoiceAsPdf(CompletedOrderDto completedOrderDto);
}
