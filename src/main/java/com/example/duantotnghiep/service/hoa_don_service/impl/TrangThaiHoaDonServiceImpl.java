package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import com.example.duantotnghiep.repository.HoaDonChiTietRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.TrangThaiHoaDonRepository;
import com.example.duantotnghiep.request.ConfirmOrderClientRequest;
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
import java.sql.Timestamp;
import java.util.Date;
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
        }
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse confirmOrderClient(UUID hoadonId, ConfirmOrderClientRequest request, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);

        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();

            hoaDon.setTenNguoiShip(request.getHoVaTenNguoiShip());
            hoaDon.setTienShip(request.getTienShip());
            hoaDon.setSdtNguoiShip(request.getHoVaTenNguoiShip());
            hoaDon.setDiaChi(request.getDiaChi());
            hoaDon.setSdtNguoiNhan(request.getSdtClient());
            hoaDon.setTenNguoiNhan(request.getHoVatenClient());
            hoaDon.setEmail(request.getEmail());
            hoaDon.setNgayCapNhap(timestamp);
            System.out.println("Tên khách: "+request.getHoVatenClient());
            auditLogService.writeAuditLogHoadon( username, taiKhoan.getEmail(), "Cập nhật địa chỉ",
                    hoaDonOptional.get().getMa(), "Tên khách: "+  request.getHoVatenClient(), "SĐT: " +request.getSdtClient(),
                    "Tiền ship: "+ request.getTienShip(), "Địa chỉ: "+request.getDiaChi());

            if (hoaDon.getTienShip() != null) {
                hoaDon.setTienShip(request.getTienShip());
                hoaDon.setThanhTien(hoaDon.getThanhTien().subtract(hoaDon.getTienShip()).add(request.getTienShip()));
            }
            if (hoaDon.getTienShip() == null) {
                hoaDon.setTienShip(request.getTienShip());
                hoaDon.setThanhTien(hoaDon.getThanhTien().add(request.getTienShip()));
            }
            hoaDonRepository.save(hoaDon);

            return MessageResponse.builder().message("Cập nhập thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy hóa đơn").build();
        }
    }

}
