package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.config.VnPayConfig;
import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ConfirmOrderClientRequest;
import com.example.duantotnghiep.request.ConfirmOrderDeliver;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.hoa_don_service.TrangThaiHoaDonService;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TrangThaiHoaDonServiceImpl implements TrangThaiHoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Override
    public MessageResponse confirmOrder(UUID hoadonId, TrangThaiHoaDonRequest request, String name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(name).get();
        List<HoaDonChiTiet> hoaDonChiTietList = hoaDonChiTietRepository.findAllByHoaDon(hoaDonOptional.get());

        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();

            // Tạo một bản ghi TrangThaiHoaDon mới
            TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
            trangThaiHoaDon.setId(UUID.randomUUID());
            trangThaiHoaDon.setHoaDon(hoaDon);
            trangThaiHoaDon.setThoiGian(timestamp);
            trangThaiHoaDon.setTrangThai(request.getNewTrangThai());
            trangThaiHoaDon.setGhiChu(request.getGhiChu());

            // Cập nhật trạng thái của Hóa Đơn
            hoaDon.setTrangThai(request.getNewTrangThai());
            hoaDon.setTaiKhoanNhanVien(taiKhoan);


            hoaDonChiTietList.stream()
                    .filter(hoaDonChiTiet -> hoaDonChiTiet.getTrangThai() != 7)
                    .forEach(hoaDonChiTiet -> {
                        hoaDonChiTiet.setTrangThai(request.getNewTrangThai());
                        hoaDonChiTietRepository.save(hoaDonChiTiet);
                    });

            // Lưu cả hai bảng
            hoaDonRepository.save(hoaDon);
            trangThaiHoaDonRepository.save(trangThaiHoaDon);
            if (request.getNewTrangThai() == 5) {
                LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
                loaiHinhThucThanhToan.setId(UUID.randomUUID());
                loaiHinhThucThanhToan.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
                loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
                loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

                HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
                hinhThucThanhToan.setId(UUID.randomUUID());
                hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
                hinhThucThanhToan.setTaiKhoan(hoaDon.getTaiKhoanKhachHang());
                hinhThucThanhToan.setTongSoTien(hoaDon.getThanhTien());
                hinhThucThanhToan.setGhiChu("");
                hinhThucThanhToan.setPhuongThucThanhToan(1);
                hinhThucThanhToan.setCodeTransaction(VnPayConfig.getRandomNumber(8));
                hinhThucThanhToan.setHoaDon(hoaDon);
                hinhThucThanhToan.setTrangThai(1);
                hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
                hinhThucThanhToanRepository.save(hinhThucThanhToan);
            }
        }
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse confirmOrderClient(UUID hoadonId, ConfirmOrderClientRequest request, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoadonId);

        if (hoaDon.isPresent()) {
            hoaDon.get().setTienShip(request.getTienShip());
            hoaDon.get().setDiaChi(request.getDiaChi());
            hoaDon.get().setSdtNguoiNhan(request.getSdtClient());
            hoaDon.get().setTenNguoiNhan(request.getHoVatenClient());
            hoaDon.get().setEmail(request.getEmail());
            hoaDon.get().setNgayCapNhap(timestamp);


            BigDecimal tongTien = BigDecimal.ZERO;
            for (HoaDonChiTiet hdct: hoaDon.get().getHoaDonChiTietList()) {
                BigDecimal donGiaSauGiam = hdct.getDonGiaSauGiam() != null ? hdct.getDonGiaSauGiam() : BigDecimal.ZERO;
                Integer soLuong = hdct.getSoLuong() != null ? hdct.getSoLuong() : 0;
                tongTien = tongTien.add(donGiaSauGiam.multiply(new BigDecimal(soLuong))).subtract(hoaDon.get().getTienGiamGia());
            }

            hoaDon.get().setThanhTien(tongTien.add(request.getTienShip()));

            hoaDon.get().setTienShip(request.getTienShip());
            hoaDon.get().setDiaChi(request.getDiaChi());
            hoaDon.get().setSdtNguoiNhan(request.getSdtClient());
            hoaDon.get().setTenNguoiNhan(request.getHoVatenClient());
            hoaDon.get().setEmail(request.getEmail());
            hoaDon.get().setNgayCapNhap(timestamp);
            System.out.println("Tên khách: "+request.getHoVatenClient());
            auditLogService.writeAuditLogHoadon( username, taiKhoan.getEmail(), "Cập nhật địa chỉ",
                    hoaDon.get().getMa(), "Tên khách: "+  request.getHoVatenClient(), "SĐT: " +request.getSdtClient(),
                    "Tiền ship: "+ request.getTienShip(), "Địa chỉ: "+request.getDiaChi());

            if (hoaDon.get().getTienShip() != null) {
                hoaDon.get().setTienShip(request.getTienShip());
                hoaDon.get().setThanhTien(hoaDon.get().getThanhTien().subtract(hoaDon.get().getTienShip()).add(request.getTienShip()));
            }
            if (hoaDon.get().getTienShip() == null) {
                hoaDon.get().setTienShip(request.getTienShip());
                hoaDon.get().setThanhTien(hoaDon.get().getThanhTien().add(request.getTienShip()));
            }
            hoaDonRepository.save(hoaDon.get());

            hoaDonRepository.save(hoaDon.get());
            return MessageResponse.builder().message("Cập nhập thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy hóa đơn").build();
        }
    }

    @Override
    public MessageResponse confirmOrderDeliver(UUID hoadonId, ConfirmOrderDeliver request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoadonId);

        if (hoaDon.isPresent()) {
            hoaDon.get().setSdtNguoiShip(request.getSoDienThoaiGiao());
            hoaDon.get().setTenNguoiShip(request.getTenNguoiGiao());
            hoaDon.get().setNgayCapNhap(timestamp);
            hoaDonRepository.save(hoaDon.get());
            return MessageResponse.builder().message("Cập nhập thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy hóa đơn").build();
        }
    }

}
