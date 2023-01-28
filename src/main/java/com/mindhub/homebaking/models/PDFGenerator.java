package com.mindhub.homebaking.models;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Set;
import java.util.stream.Stream;

public class PDFGenerator {

    public static void downloadPdf(Set<Transaction> transactions, Client clientCurrent, HttpServletResponse httpServletResponse) throws Exception {

        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Content-Disposition", "attachment; filename=transaction.pdf");// encanbezado del pdf
        Font titleFont = FontFactory.getFont("Helvetica", 20, Color.BLUE);
        Font bodyFont = FontFactory.getFont("Helvetica", 14, Color.BLACK);
        Font subtitleFont = FontFactory.getFont("Helvetica", 16, Color.BLACK);
        Paragraph paragraphTitle = new Paragraph("MINDHUB-BANK", titleFont );
        Paragraph paragraphBody= new Paragraph("CLIENT: " + clientCurrent.getFirstName() + " " + clientCurrent.getLastName() , bodyFont);
        Paragraph paragraphSubtitle= new Paragraph("Your Transactions:", subtitleFont);
        paragraphSubtitle.setSpacingAfter(20);

        PdfPTable transactionsTable = new PdfPTable(4);
        Stream.of("Amount","Description", "Type", "Date").forEach(transactionsTable::addCell);
        transactions.forEach(transaction -> {
            transactionsTable.addCell( String.valueOf(transaction.getAmount()));
            transactionsTable.addCell(transaction.getDescription());
            transactionsTable.addCell( String.valueOf(transaction.getType()));
            transactionsTable.addCell( String.valueOf(transaction.getDate()));
        });

        Document documentPDF = new Document();
        documentPDF.setPageSize(PageSize.A4);

        PdfWriter.getInstance(documentPDF,httpServletResponse.getOutputStream());
        documentPDF.open();
        documentPDF.add(paragraphTitle);
        documentPDF.add(paragraphBody);
        documentPDF.add(paragraphSubtitle);
        documentPDF.add(transactionsTable);
        documentPDF.close();
    }
}
