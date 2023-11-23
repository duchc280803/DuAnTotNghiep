package com.example.duantotnghiep.pdf;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.service.pdf.impl.PdfServiceImpl;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.printing.PDFPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.print.PrintService;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/pdf/")
public class PdfController {

    @Autowired
    private PdfServiceImpl pdfService;

    @GetMapping("/export")
    public ResponseEntity<String> exportPdf(HttpServletResponse response, @RequestParam UUID id) {
        try {
            Optional<HoaDon> hoaDonOptional = pdfService.hoaDon(id);
            if (hoaDonOptional.isPresent()) {
                HoaDon hoaDon = hoaDonOptional.get();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PDDocument document = pdfService.exportPDdocument(response, hoaDon);
                document.save(baos);
                document.close();

                String filePath = savePdfToFileSystem(baos.toByteArray()); // Lưu tệp vào hệ thống tệp

                return new ResponseEntity<>("Tệp PDF đã được lưu tại: " + filePath, HttpStatus.OK);
            }
            return new ResponseEntity<>("Không tìm thấy hoá đơn.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý các ngoại lệ
            return new ResponseEntity<>("Lỗi khi tạo hoặc lưu tệp PDF.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String savePdfToFileSystem(byte[] pdfData) throws IOException {
        String folderPath = "C:\\DuAnTotNghiep"; // Đường dẫn thư mục
        String fileName = "invoice.pdf"; // Tên tệp PDF
        String fullPath = folderPath + File.separator + fileName; // Đường dẫn đầy đủ

        // Ghi tệp PDF vào hệ thống tệp
        File file = new File(fullPath);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(pdfData);
        fos.close();

        return fullPath;
    }

}
