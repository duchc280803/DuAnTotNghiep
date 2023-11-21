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
import com.example.duantotnghiep.service.hoa_don_service.TrangThaiHoaDonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public MessageResponse confirmOrderClient(UUID hoadonId, ConfirmOrderClientRequest request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoadonId);
        hoaDon.get().setTenNguoiShip(request.getHoVaTenNguoiShip());
        hoaDon.get().setTienShip(request.getTienShip());
        hoaDon.get().setSdtNguoiShip(request.getSoDienThoai());
        hoaDon.get().setDiaChi(request.getDiaChi());
        hoaDon.get().setNgayCapNhap(timestamp);
        hoaDon.get().setThanhTien(hoaDon.get().getThanhTien().add(request.getTienShip()));
        hoaDonRepository.save(hoaDon.get());
        return MessageResponse.builder().message("Cập nhập thành công").build();
    }

}
