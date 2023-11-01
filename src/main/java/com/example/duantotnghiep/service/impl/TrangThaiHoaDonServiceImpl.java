package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.TrangThaiHoaDonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrangThaiHoaDonServiceImpl {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Transactional
    public void updateTrangThaiHoaDon(UUID hoadonId, Integer newTrangThai, String ghiChu) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();

            // Tạo một bản ghi TrangThaiHoaDon mới
            TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
            trangThaiHoaDon.setHoaDon(hoaDon);
            trangThaiHoaDon.setThoiGian(new Date());
            trangThaiHoaDon.setTrangThai(newTrangThai);
            trangThaiHoaDon.setGhiChu(ghiChu);

            // Thêm bản ghi TrangThaiHoaDon vào danh sách của Hóa Đơn
            hoaDon.getTrangThaiHoaDonList().add(trangThaiHoaDon);

            // Cập nhật trạng thái của Hóa Đơn
            hoaDon.setTrangThai(newTrangThai);

            // Lưu cả hai bảng
            hoaDonRepository.save(hoaDon);
            trangThaiHoaDonRepository.save(trangThaiHoaDon);
        }
    }
}