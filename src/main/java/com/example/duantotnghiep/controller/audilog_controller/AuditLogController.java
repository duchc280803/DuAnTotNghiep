package com.example.duantotnghiep.controller.audilog_controller;

import com.example.duantotnghiep.entity.AuditLog;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/audilog/")
public class AuditLogController {
    private static final String BASE_DIRECTORY = "D:\\audilog";
    private static final String ADMIN_DIRECTORY = BASE_DIRECTORY + "\\admin";
    private static final String CUSTOMER_DIRECTORY = BASE_DIRECTORY + "\\customer";
    private static final String QUAN_LY_SAN_PHAM_DIRECTORY = ADMIN_DIRECTORY + "\\quanlysanpham";
    private static final String SANPHAM_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\sanpham";
    private static final String AUDIT_LOG_SANPHAM_FILE_PATH = SANPHAM_DIRECTORY + "\\audilog_sanpham.csv";
    private static final String SIZE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\size";
    private static final String CHATLIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\chatlieu";
    private static final String MAUSAC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\mausac";
    private static final String DANHMUC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\danhmuc";
    private static final String THUONGHIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\thuonghieu";
    private static final String XUATXU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\xuatxu";
    private static final String KIEUDE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\kieude";
    private static final String QUAN_LY_TAI_KHOAN_DIRECTORY = ADMIN_DIRECTORY + "\\quanlytaikhoan";
    private static final String NHANVIEN_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\nhanvien";
    private static final String KHACHHANG_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\khachhang";
    private static final String VOUCHER_DIRECTORY = ADMIN_DIRECTORY + "\\voucher";
    private static final String HOADON_DIRECTORY = ADMIN_DIRECTORY + "\\hoadon";
    private static final String HOADON_CHI_TIET_DIRECTORY = ADMIN_DIRECTORY + "\\hoadonchitiet";
    private static final String KHUYENMAI_DIRECTORY = ADMIN_DIRECTORY + "\\khuyenmai";
    private static final String AUDIT_LOG_HOADON_FILE_PATH = HOADON_DIRECTORY + "\\audilog_hoadon.csv";
    private static final String AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH = HOADON_CHI_TIET_DIRECTORY + "\\audilog_hoadonchitiet.csv";
    private static final String AUDIT_LOG_KHUYENMAI_FILE_PATH = KHUYENMAI_DIRECTORY + "\\audilog_khuyenmai.csv";
    private static final String AUDIT_LOG_SIZE_FILE_PATH = SIZE_DIRECTORY + "\\audilog_size.csv";
    private static final String AUDIT_LOG_CHATLIEU_FILE_PATH = CHATLIEU_DIRECTORY + "\\audilog_chatlieu.csv";
    private static final String AUDIT_LOG_MAUSAC_FILE_PATH = MAUSAC_DIRECTORY + "\\audilog_mausac.csv";
    private static final String AUDIT_LOG_DANHMUC_FILE_PATH = DANHMUC_DIRECTORY + "\\audilog_danhmuc.csv";
    private static final String AUDIT_LOG_THUONGHIEU_FILE_PATH = THUONGHIEU_DIRECTORY + "\\audilog_thuonghieu.csv";
    private static final String AUDIT_LOG_XUATXU_FILE_PATH = XUATXU_DIRECTORY + "\\audilog_xuatxu.csv";
    private static final String AUDIT_LOG_KIEUDE_FILE_PATH = KIEUDE_DIRECTORY + "\\audilog_kieude.csv";
    private static final String AUDIT_LOG_NHANVIEN_FILE_PATH = NHANVIEN_DIRECTORY + "\\audilog_nhanvien.csv";
    private static final String AUDIT_LOG_KHACHHANG_FILE_PATH = KHACHHANG_DIRECTORY + "\\audilog_khachhang.csv";
    private static final String AUDIT_LOG_VOUCHER_FILE_PATH = VOUCHER_DIRECTORY + "\\audilog_voucher.csv";
    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("/hoadon")
    public List<AuditLog> getAuditLogHoadon() {
        try {
            return auditLogService.readAuditLogHoadon();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/hoadonchitiet")
    public List<AuditLog> getAuditLogHoadonChiTiet() {
        try {
            return auditLogService.readAuditLogHoadonChiTiet();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }

    // API để đọc thông tin từ file CSV khuyến mãi và hiển thị
    @GetMapping("/khuyenmai")
    public List<AuditLog> getAuditLogKhuyenmai() {
        try {
            return auditLogService.readAuditLogKhuyenmai();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/sanpham")
    public List<AuditLog> getAuditLogSanPham() {
        try {
            return auditLogService.readAuditLogSanPham();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    // API để đọc thông tin từ file CSV khuyến mãi và hiển thị
    @GetMapping("/chatlieu")
    public List<AuditLog> getAuditLogChatLieu() {
        try {
            return auditLogService.readAuditLogChatLieu();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    // API để đọc thông tin từ file CSV khuyến mãi và hiển thị
    @GetMapping("/size")
    public List<AuditLog> getAuditLogSize() {
        try {
            return auditLogService.readAuditLogSize();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    // API để đọc thông tin từ file CSV khuyến mãi và hiển thị
    @GetMapping("/mausac")
    public List<AuditLog> getAuditLogMauSac() {
        try {
            return auditLogService.readAuditLogMauSac();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    // API để đọc thông tin từ file CSV khuyến mãi và hiển thị
    @GetMapping("/danhmuc")
    public List<AuditLog> getAuditLogDanhMuc() {
        try {
            return auditLogService.readAuditLogDanhMuc();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/thuonghieu")
    public List<AuditLog> getAuditLogThuongHieu() {
        try {
            return auditLogService.readAuditLogThuongHieu();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/xuatxu")
    public List<AuditLog> getAuditLogXuatXu() {
        try {
            return auditLogService.readAuditLogXuatXu();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/kieude")
    public List<AuditLog> getAuditLogKieuDe() {
        try {
            return auditLogService.readAuditLogKieuDe();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/nhanvien")
    public List<AuditLog> getAuditLogNhanVien() {
        try {
            return auditLogService.readAuditLogNhanVien();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/voucher")
    public List<AuditLog> getAuditLogVoucher() {
        try {
            return auditLogService.readAuditLogVoucher();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
    @GetMapping("/khachhang")
    public List<AuditLog> getAuditLogKhachHang() {
        try {
            return auditLogService.readAuditLogKhachHang();
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Xử lý lỗi, có thể trả về một đối tượng ResponseEntity để thông báo lỗi cho client
            return null;
        }
    }
// tim theo thoi gian
    @GetMapping("/hoadonseach")
    public List<AuditLog> getAuditLogHoadons(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_HOADON_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/sanphamseach")
    public List<AuditLog> getAuditLogSanPhams(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_SANPHAM_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/khuyenmaiseach")
    public List<AuditLog> getAuditLogkhuyenmais(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KHUYENMAI_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/chatlieusearch")
    public List<AuditLog> getAuditLogChatLieus(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_CHATLIEU_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/sizesearch")
    public List<AuditLog> getAuditLogSizes(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_SIZE_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/mausacsearch")
    public List<AuditLog> getAuditLogMauSacs(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_MAUSAC_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/danhmucsearch")
    public List<AuditLog> getAuditLogDanhMucs(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_DANHMUC_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/thuonghieusearch")
    public List<AuditLog> getAuditLogThuongHieus(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_THUONGHIEU_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/xuatxusearch")
    public List<AuditLog> getAuditLogXuatXus(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_XUATXU_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/kieudesearch")
    public List<AuditLog> getAuditLogKieuDes(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KIEUDE_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
    @GetMapping("/nhanviensearch")
    public List<AuditLog> getAuditLogNhanViens(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_NHANVIEN_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/khachhangsearch")
    public List<AuditLog> getAuditLogKhachHangs(
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_KHACHHANG_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/vouchersearch")
    public List<AuditLog> getAuditLogVouchers(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_VOUCHER_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/auditlogbydate")
    public List<AuditLog> getAuditLogByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
        try {
            return auditLogService.readAuditLogByDate(AUDIT_LOG_VOUCHER_FILE_PATH, searchDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }



    @GetMapping("/hoadonchitietsearch")
    public List<AuditLog> getAuditLogHoaDonChiTiet(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        try {
            LocalDateTime startTime = startDate.atStartOfDay();
            LocalDateTime endTime = endDate.atTime(LocalTime.MAX);
            return auditLogService.readAuditLogByTimeRange(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH, startTime, endTime);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }

    @GetMapping("/auditlogbydatehoadonchitiet")
    public List<AuditLog> getAuditLogByDateHoaDonChiTiet(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate) {
        try {
            return auditLogService.readAuditLogByDate(AUDIT_LOG_HOADON_CHI_TIET_FILE_PATH, searchDate);
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
            // Handle errors, possibly return a ResponseEntity to inform the client about the error
            return null;
        }
    }
}

