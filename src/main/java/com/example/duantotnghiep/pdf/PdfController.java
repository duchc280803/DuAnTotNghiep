package com.example.duantotnghiep.pdf;

import com.example.duantotnghiep.service.pdf.impl.PdfServiceImpl;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.PrintException;
import javax.print.PrintService;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pdf/")
public class PdfController {

    @Autowired
    private PdfServiceImpl pdfService;

    @GetMapping("/print/{id}")
    public String PrintBillInShop(HttpServletResponse response, @PathVariable("id") UUID id)
            throws InvalidPasswordException, IOException, PrintException, DocumentException {
        try {
            response.setContentType("application/pdf");

            PrintService myPrintService = pdfService.findPrintService("My Windows printer Name");
            PrinterJob job = PrinterJob.getPrinterJob();
//            job.setPageable(new PDFPageable(pdfService.exportPDdocument(response, id)));
            job.printDialog();
            job.setPrintService(myPrintService);
            job.print();

        } catch (Exception e) {
            return "Lỗi hóa đơn";
        }
        return "Đã in";
    }
}
