package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.TrangThaiHoaDonRepository;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.hoa_don_service.TrangThaiHoaDonService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrangThaiHoaDonServiceImpl implements TrangThaiHoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public MessageResponse confirmOrder(UUID hoadonId, TrangThaiHoaDonRequest request, String name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(name).get();
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
            // Lưu cả hai bảng
            hoaDonRepository.save(hoaDon);
            trangThaiHoaDonRepository.save(trangThaiHoaDon);
        }
        return MessageResponse.builder().message("Thêm thành công").build();
    }

}
