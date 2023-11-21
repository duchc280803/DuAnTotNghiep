package com.example.duantotnghiep.service.audi_log_service;
import com.example.duantotnghiep.entity.AuditLog;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AuditLogService {
    private static final String BASE_DIRECTORY = "D:\\audilog";
    private static final String ADMIN_DIRECTORY = BASE_DIRECTORY +  "\\admin";
    private static final String CUSTOMER_DIRECTORY = BASE_DIRECTORY +  "\\customer";
    private static final String  QUAN_LY_SAN_PHAM_DIRECTORY = ADMIN_DIRECTORY + "\\quanlysanpham";
    private static final String SANPHAM_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\sanpham";
    private static final String AUDIT_LOG_SANPHAM_FILE_PATH = SANPHAM_DIRECTORY + "\\audilog_sanpham_%s.csv";
    private static final String SIZE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY+ "\\size";
    private static final String CHATLIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\chatlieu";
    private static final String MAUSAC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY+ "\\mausac";
    private static final String DANHMUC_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\danhmuc";
    private static final String THUONGHIEU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\thuonghieu";
    private static final String XUATXU_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY + "\\xuatxu";
    private static final String KIEUDE_DIRECTORY = QUAN_LY_SAN_PHAM_DIRECTORY+ "\\kieude";
    private static final String QUAN_LY_TAI_KHOAN_DIRECTORY = ADMIN_DIRECTORY + "\\quanlytaikhoan";
    private static final String NHANVIEN_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\nhanvien";
    private static final String KHACHHANG_DIRECTORY = QUAN_LY_TAI_KHOAN_DIRECTORY + "\\khachhang";
    private static final String VOUCHER_DIRECTORY = ADMIN_DIRECTORY + "\\voucher";
    private static final String HOADON_DIRECTORY = ADMIN_DIRECTORY + "\\hoadon";
    private static final String KHUYENMAI_DIRECTORY = ADMIN_DIRECTORY  + "\\khuyenmai";
    private static final String AUDIT_LOG_HOADON_FILE_PATH = HOADON_DIRECTORY + "\\audilog_hoadon_%s.csv";
    private static final String AUDIT_LOG_KHUYENMAI_FILE_PATH = KHUYENMAI_DIRECTORY + "\\audilog_khuyenmai_%s.csv";
    private static final String AUDIT_LOG_SIZE_FILE_PATH = SIZE_DIRECTORY + "\\audilog_size_%s.csv";
    private static final String AUDIT_LOG_CHATLIEU_FILE_PATH = CHATLIEU_DIRECTORY + "\\audilog_chatlieu_%s.csv";
    private static final String AUDIT_LOG_MAUSAC_FILE_PATH = MAUSAC_DIRECTORY + "\\audilog_mausac_%s.csv";
    private static final String AUDIT_LOG_DANHMUC_FILE_PATH = DANHMUC_DIRECTORY + "\\audilog_danhmuc_%s.csv";
    private static final String AUDIT_LOG_THUONGHIEU_FILE_PATH = THUONGHIEU_DIRECTORY + "\\audilog_thuonghieu_%s.csv";
    private static final String AUDIT_LOG_XUATXU_FILE_PATH = XUATXU_DIRECTORY + "\\audilog_xuatxu_%s.csv";
    private static final String AUDIT_LOG_KIEUDE_FILE_PATH = KIEUDE_DIRECTORY + "\\audilog_kieude_%s.csv";
    private static final String AUDIT_LOG_NHANVIEN_FILE_PATH = NHANVIEN_DIRECTORY + "\\audilog_nhanvien_%s.csv";
    private static final String AUDIT_LOG_KHACHHANG_FILE_PATH = KHACHHANG_DIRECTORY + "\\audilog_khachhang_%s.csv";
    private static final String AUDIT_LOG_VOUCHER_FILE_PATH = VOUCHER_DIRECTORY + "\\audilog_voucher_%s.csv";
    public AuditLogService() {
        createDirectories();
        createAuditLogFileIfNotExists(AUDIT_LOG_HOADON_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KHUYENMAI_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_SANPHAM_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_SIZE_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_CHATLIEU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_MAUSAC_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_DANHMUC_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_THUONGHIEU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_XUATXU_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KIEUDE_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_NHANVIEN_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_KHACHHANG_FILE_PATH);
        createAuditLogFileIfNotExists(AUDIT_LOG_VOUCHER_FILE_PATH);
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Paths.get(BASE_DIRECTORY));
            Files.createDirectories(Paths.get(ADMIN_DIRECTORY));
            Files.createDirectories(Paths.get(CUSTOMER_DIRECTORY));
            Files.createDirectories(Paths.get(HOADON_DIRECTORY));
            Files.createDirectories(Paths.get(QUAN_LY_SAN_PHAM_DIRECTORY));
            Files.createDirectories(Paths.get(SANPHAM_DIRECTORY));
            Files.createDirectories(Paths.get(SIZE_DIRECTORY));
            Files.createDirectories(Paths.get( CHATLIEU_DIRECTORY));
            Files.createDirectories(Paths.get(MAUSAC_DIRECTORY));
            Files.createDirectories(Paths.get(DANHMUC_DIRECTORY ));
            Files.createDirectories(Paths.get(THUONGHIEU_DIRECTORY));
            Files.createDirectories(Paths.get(XUATXU_DIRECTORY));
            Files.createDirectories(Paths.get(KIEUDE_DIRECTORY));
            Files.createDirectories(Paths.get(QUAN_LY_TAI_KHOAN_DIRECTORY));
            Files.createDirectories(Paths.get(NHANVIEN_DIRECTORY ));
            Files.createDirectories(Paths.get(KHACHHANG_DIRECTORY ));
            Files.createDirectories(Paths.get(VOUCHER_DIRECTORY ));
            Files.createDirectories(Paths.get(KHUYENMAI_DIRECTORY));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createAuditLogFileIfNotExists(String filePath) {
        try {
            File auditLogFile = new File(String.format(filePath, getCurrentDate()));
            if (!auditLogFile.exists()) {
                Files.createFile(Paths.get(String.format(filePath, getCurrentDate())));
                writeAuditLogHeader(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeAuditLogHeader(String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Action", "Username", "Email", "Timestamp"};
            writer.writeNext(header);
        }
    }

    public List<AuditLog> readAuditLog(String filePath) throws IOException, CsvValidationException {
        List<AuditLog> auditLogList = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            reader.skip(1);

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                String action = nextLine[0];
                String username = nextLine[1];
                String email = nextLine[2];
                LocalDateTime timestamp = LocalDateTime.parse(nextLine[3]);
                auditLogList.add(new AuditLog(action, username, email, timestamp));
            }
        }
        return auditLogList;
    }

    public void writeAuditLogHoadon(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_HOADON_FILE_PATH);
    }

    public void writeAuditLogSanPham(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_SANPHAM_FILE_PATH);
    }

    public void writeAuditLogSize(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_SIZE_FILE_PATH);
    }

    public void writeAuditLogChatlieu(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_CHATLIEU_FILE_PATH);
    }

    public void writeAuditLogMausac(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_MAUSAC_FILE_PATH);
    }

    public void writeAuditLogDanhmuc(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_DANHMUC_FILE_PATH);
    }

    public void writeAuditLogThuonghieu(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_THUONGHIEU_FILE_PATH);
    }

    public void writeAuditLogXuatxu(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_XUATXU_FILE_PATH);
    }

    public void writeAuditLogKieude(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_KIEUDE_FILE_PATH);
    }

    public void writeAuditLogNhanvien(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_NHANVIEN_FILE_PATH);
    }

    public void writeAuditLogKhachhang(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_KHACHHANG_FILE_PATH);
    }

    public void writeAuditLogVoucher(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_VOUCHER_FILE_PATH);
    }


    public void writeAuditLogKhuyenmai(String action, String username, String email) throws IOException, CsvValidationException {
        writeAuditLog(action, username, email, AUDIT_LOG_KHUYENMAI_FILE_PATH);
    }

    private void writeAuditLog(String action, String username, String email, String filePath) throws IOException, CsvValidationException {
        List<AuditLog> auditLogList = readAuditLog(filePath);
        auditLogList.add(new AuditLog(action, username, email, LocalDateTime.now()));
        writeAuditLogToFile(auditLogList, filePath);
    }

    private void writeAuditLogToFile(List<AuditLog> auditLogList, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(String.format(filePath, getCurrentDate()), true))) {
            // Ghi header nếu file chưa có dữ liệu
            if (new File(String.format(filePath, getCurrentDate())).length() == 0) {
                String[] header = {"Action", "Username", "Email", "Timestamp"};
                writer.writeNext(header);
            }

            // Ghi dữ liệu mới vào cuối file
            for (AuditLog auditLog : auditLogList) {
                String[] data = {auditLog.getAction(), auditLog.getUsername(), auditLog.getPassword(), auditLog.getTimestamp().toString()};
                writer.writeNext(data);
            }
        }
    }

    private String getCurrentDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDateTime.now().format(formatter);
    }

}