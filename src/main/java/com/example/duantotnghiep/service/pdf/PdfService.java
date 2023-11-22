package com.example.duantotnghiep.service.pdf;

import com.example.duantotnghiep.entity.HoaDon;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;
import java.util.UUID;

public interface PdfService {

    PDDocument exportPDdocument(HttpServletResponse response, HoaDon hoaDon) throws IOException, DocumentException;

    void export(HttpServletResponse response, HoaDon hoaDon) throws IOException, DocumentException;

    void writeHeaderPage(Document document, BaseFont arial, HoaDon hoaDon) throws DocumentException;

    void designTable(PdfPTable table, Document document, BaseFont airal, HoaDon hoaDon) throws DocumentException;

    void writeTableHeader(PdfPTable table, BaseFont arial, Document document) throws DocumentException;

    void writeTableData(PdfPTable table, HoaDon hoaDon, BaseFont arial, Document document) throws DocumentException;

    void writeEndPage(Document document, BaseFont arial, HoaDon hoaDon) throws DocumentException;

    void writeReturnPolicy(Document document, BaseFont arial) throws DocumentException;

//    void writeTableReturn(HoaDon hoaDon, BaseFont arial, Document document) throws DocumentException;

//    void writeTableReturnHeader(PdfPTable tableReturn, BaseFont arial);

}
