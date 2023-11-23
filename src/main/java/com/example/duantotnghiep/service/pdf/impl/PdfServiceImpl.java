package com.example.duantotnghiep.service.pdf.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.repository.HoaDonChiTietRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.service.pdf.PdfService;
import com.example.duantotnghiep.util.Constant;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    private static final String FONT_ARIAL = "C:\\DuAnTotNghiep\\lib\\font\\ArialCE.ttf";
    private static final Locale LOCALE_VN = new Locale("vi", "VN");
    private static final NumberFormat CURRENCY_VN = NumberFormat.getCurrencyInstance(LOCALE_VN);

    /**
     * Tìm kiếm máy in
     *
     * @param printerName
     * @return
     */
    @Override
    public PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }

    /**
     * xuất ra file excel
     *
     * @param response
     * @param hoaDon
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @Override
    public PDDocument exportPDdocument(HttpServletResponse response, HoaDon hoaDon) throws IOException, DocumentException {
        PDDocument document = new PDDocument();
        export(response, hoaDon);
        document.save(response.getOutputStream());
        document.close();
        return document;
    }

    /**
     * Xuất ra file excel
     *
     * @param response
     * @param hoaDon
     * @throws DocumentException
     * @throws IOException
     * @throws NumberFormatException
     */
    @Override
    public void export(HttpServletResponse response, HoaDon hoaDon)
            throws DocumentException, IOException, NumberFormatException {
        Document document = new Document(PageSize.A4);
        PdfPTable table = new PdfPTable(6);
        BaseFont newFont = BaseFont.createFont(FONT_ARIAL, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        document.open();

        writeHeaderPage(document, newFont, hoaDon);
        designTable(table, document, newFont, hoaDon);
        writeEndPage(document, newFont, hoaDon);

        document.close();
    }

    private Paragraph createParagraph(String text, BaseFont font, int fontSize, int style, int alignment) {
        Paragraph paragraph = new Paragraph(text, new Font(font, fontSize, style));
        paragraph.setAlignment(alignment);
        return paragraph;
    }

    /**
     * Tạo phần đầu cho trang pdf
     *
     * @param document
     * @param arial
     * @param hoaDon
     * @throws DocumentException
     */
    @Override
    public void writeHeaderPage(Document document, BaseFont arial, HoaDon hoaDon) throws DocumentException {
        String phoneNumberText = "Số điện thoại: 0971066455";
        String emailText = "Email: ";
        String addressText = "Địa chỉ: Cổng số 1, Tòa nhà FPT Polytechnic, 13 phố Trịnh Văn Bô, phường Phương Canh, quận Nam Từ Liêm, TP Hà Nội";

        Paragraph titleName = createParagraph("Niceshoe.snk", arial, 25, Font.BOLD, Paragraph.ALIGN_CENTER);
        Paragraph phoneNumber = createParagraph(phoneNumberText, arial, 12, Font.BOLD, Paragraph.ALIGN_CENTER);
        Paragraph email = createParagraph(emailText, arial, 12, Font.BOLD, Paragraph.ALIGN_CENTER);
        Paragraph address = createParagraph(addressText, arial, 12, Font.BOLD, Paragraph.ALIGN_CENTER);
        // Tạo các đối tượng Paragraph còn lại bằng cách sử dụng hàm createParagraph

        // Set căn chỉnh và khoảng cách giữa các đối tượng
        setAlignmentAndSpacing(titleName, Paragraph.ALIGN_CENTER, 0);
        setAlignmentAndSpacing(phoneNumber, Paragraph.ALIGN_CENTER, 0);
        setAlignmentAndSpacing(email, Paragraph.ALIGN_CENTER, 0);
        setAlignmentAndSpacing(address, Paragraph.ALIGN_CENTER, 0);
        // Set căn chỉnh và khoảng cách giữa các đối tượng còn lại

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1f, 3f, 1f});
        table.setSpacingBefore(20);

        PdfPCell cell = new PdfPCell();
        PdfPCell cellQR = new PdfPCell();
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBorder(0);
        cellQR.setBorder(0);
        cellQR.setRowspan(4);
        table.addCell(cellQR);

        // Thêm các đối tượng Paragraph vào table
        addParagraphToTable(table, cell, phoneNumber);
        addEmptyCellToTable(table, cell);
        addParagraphToTable(table, cell, email);
        addEmptyCellToTable(table, cell);
        addParagraphToTable(table, cell, address);
        addEmptyCellToTable(table, cell);

        document.add(titleName);
        document.add(table);
        document.add(createParagraph("HÓA ĐƠN BÁN HÀNG", arial, 20, Font.BOLD, Paragraph.ALIGN_CENTER));
        document.add(createParagraph(hoaDon.getMa(), arial, 12, Font.NORMAL, Paragraph.ALIGN_CENTER));
        document.add(createParagraph("Ngày mua: " + hoaDon.getNgayTao(), arial, 12, Font.NORMAL, Paragraph.ALIGN_LEFT));
        document.add(createParagraph("Khách hàng: " + hoaDon.getTenNguoiNhan(), arial, 12, Font.NORMAL, Paragraph.ALIGN_LEFT));
        document.add(createParagraph("Địa chỉ: " + hoaDon.getDiaChi(), arial, 12, Font.NORMAL, Paragraph.ALIGN_LEFT));
        document.add(createParagraph("Số điện thoại: " + hoaDon.getSdtNguoiNhan(), arial, 12, Font.NORMAL, Paragraph.ALIGN_LEFT));
        document.add(createParagraph("Nhân viên bán hàng: " + hoaDon.getTaiKhoanNhanVien().getName(), arial, 12, Font.NORMAL, Paragraph.ALIGN_LEFT));
    }


    /**
     * Tạo table những sản phẩm khách hàng mua
     *
     * @param table
     * @param document
     * @param airal
     * @param hoaDon
     * @throws DocumentException
     */
    @Override
    public void designTable(PdfPTable table, Document document, BaseFont airal, HoaDon hoaDon) throws DocumentException {
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.5f, 5f, 2f, 3.5f, 1.5f, 4f});
        table.setSpacingBefore(10);

        writeTableHeader(table, airal, document);
        writeTableData(table, hoaDon, airal, document);
    }

    /**
     * Vùng header table những sản phẩm khách hàng mua
     *
     * @param table
     * @param arial
     * @param document
     */
    @Override
    public void writeTableHeader(PdfPTable table, BaseFont arial, Document document) throws DocumentException {
        String[] headerTitles = {"STT", "Sản phẩm", "Số lượng", "Đơn giá", "Thuế", "Thành tiền"};

        Paragraph productRefund = createParagraph("DANH SÁCH SẢN PHẨM KHÁCH HÀNG MUA", arial, 12, Font.BOLD, Paragraph.ALIGN_CENTER);
        productRefund.setSpacingBefore(15.0f);
        document.add(productRefund);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        for (String title : headerTitles) {
            cell.setPhrase(new Phrase(title, new Font(arial, 12, Font.BOLD)));
            table.addCell(cell);
        }
    }

    /**
     * Vùng data table những sản phẩm khách hàng mua
     *
     * @param table
     * @param hoaDon
     * @param arial
     * @param document
     * @throws DocumentException
     */
    @Override
    public void writeTableData(PdfPTable table, HoaDon hoaDon, BaseFont arial, Document document) throws DocumentException {
        Double total = 0.0;

        List<HoaDonChiTiet> details = hoaDonChiTietRepository.findByHoaDon(hoaDon);

        for (int i = 0; i < details.size(); i++) {
            HoaDonChiTiet detail = details.get(i);

            PdfPCell cell = new PdfPCell();
            cell.setPadding(5);

            cell.setPhrase(new Phrase((i + 1) + "", new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(detail.getSanPhamChiTiet().getSanPham().getTenSanPham(), new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(detail.getSoLuong() + "", new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            cell.setPhrase(new Phrase(CURRENCY_VN.format(detail.getDonGiaSauGiam()), new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            // Cần tạo một PdfPCell mới cho mỗi ô trong bảng
            cell = new PdfPCell();
            cell.setPadding(5);

//        cell.setPhrase(new Phrase(String.format("%,.0f", detail.getTax()) + "%", new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            Double intoMoney = detail.getDonGiaSauGiam().doubleValue() * detail.getSoLuong();
            cell.setPhrase(new Phrase(CURRENCY_VN.format(intoMoney), new Font(arial, 11)));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            total += intoMoney;
        }

        PdfPCell cellTotal = new PdfPCell();
        cellTotal.setPadding(5);
        cellTotal.setPhrase(new Phrase("Tổng tiền:", new Font(arial, 12, Font.BOLD)));
        cellTotal.setHorizontalAlignment(Element.ALIGN_CENTER);
        cellTotal.setColspan(5);

        PdfPCell totalCell = new PdfPCell();
        totalCell.setPadding(5);
        totalCell.setPhrase(new Phrase(CURRENCY_VN.format(total), new Font(arial, 12)));

        table.addCell(cellTotal);
        table.addCell(totalCell);

        document.add(table);
    }

    @Override
    public void writeEndPage(Document document, BaseFont arial, HoaDon hoaDon) throws DocumentException {
        PdfPTable table = new PdfPTable(6);

        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1f, 3f, 4f, 2f, 3.5f, 4f});
        table.setSpacingBefore(10);

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setPhrase(new Phrase("Tiền giảm giá: ", new Font(arial, 13)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(5);
        table.addCell(cell);

        PdfPCell cellTienGiamGia = new PdfPCell();
        cellTienGiamGia.setPadding(5);
        cellTienGiamGia.setPhrase(new Phrase(CURRENCY_VN.format(hoaDon.getTienGiamGia()), new Font(arial, 13, Font.BOLD)));
        cellTienGiamGia.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellTienGiamGia);

        cell.setPhrase(new Phrase("Phí ship: ", new Font(arial, 13)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(5);
        table.addCell(cell);

        PdfPCell cellTienShip = new PdfPCell();
        cellTienShip.setPadding(5);
        cellTienShip.setPhrase(new Phrase(CURRENCY_VN.format(hoaDon.getTienShip()), new Font(arial, 13, Font.BOLD)));
        cellTienShip.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellTienShip);

        cell.setPhrase(new Phrase("Tổng tiền phải thanh toán: ", new Font(arial, 13)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(5);
        table.addCell(cell);

        PdfPCell cellThanhTien = new PdfPCell();
        cellThanhTien.setPadding(5);
        cellThanhTien.setPhrase(new Phrase(CURRENCY_VN.format(hoaDon.getThanhTien()), new Font(arial, 13, Font.BOLD)));
        cellThanhTien.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellThanhTien);

        cell.setPhrase(new Phrase("Trạng thái đơn hàng: ", new Font(arial, 13)));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setColspan(5);
        table.addCell(cell);

        PdfPCell cellTrangThai = new PdfPCell();
        cellTrangThai.setPadding(5);
        cellTrangThai.setPhrase(new Phrase(getTrangThaiString(hoaDon.getTrangThai()), new Font(arial, 13, Font.BOLD)));
        cellTrangThai.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(cellTrangThai);

        Paragraph askThank = new Paragraph("----Cảm ơn quý khách !----", new Font(arial, 12, Font.ITALIC));
        askThank.setSpacingBefore(50);
        askThank.setAlignment(Element.ALIGN_CENTER);

        document.add(table);
        document.add(askThank);
    }

    // Hàm hỗ trợ lấy chuỗi trạng thái dựa trên số trạng thái của đơn hàng
    private String getTrangThaiString(int trangThai) {
        switch (trangThai) {
            case 1:
                return "Chờ xác nhận";
            case 2:
                return "Xác nhận";
            case 3:
                return "Chờ giao";
            case 4:
                return "Đang giao";
            case 5:
                return "Hoàn thành";
            default:
                return "Đã hủy";
        }
    }


    // Hàm thiết lập căn chỉnh và khoảng cách giữa các đối tượng Paragraph
    private void setAlignmentAndSpacing(Paragraph paragraph, int alignment, float spacingBefore) {
        paragraph.setAlignment(alignment);
        paragraph.setSpacingBefore(spacingBefore);
    }

    // Hàm thêm đối tượng Paragraph vào table
    private void addParagraphToTable(PdfPTable table, PdfPCell cell, Paragraph paragraph) {
        cell.setPhrase(new Phrase(paragraph));
        table.addCell(cell);
    }

    // Hàm thêm ô trống vào table
    private void addEmptyCellToTable(PdfPTable table, PdfPCell cell) {
        cell.setPhrase(new Phrase(""));
        table.addCell(cell);
    }

    public Optional<HoaDon> hoaDon(UUID id) {
        return hoaDonRepository.findById(id);
    }
}
