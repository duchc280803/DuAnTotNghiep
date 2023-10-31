package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;

    // minh
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public UUID taoGioHang() {
        // tìm theo username
        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

    @Override
    public MessageResponse updateGioHang(UUID idGioHang, UUID idAccount) {
        var gioHang = gioHangRepository.findById(idGioHang);
        var khachHang = taiKhoanRepository.findById(idAccount);
        gioHang.get().setTaiKhoan(khachHang.get());
        gioHangRepository.save(gioHang.get());
        return MessageResponse.builder().message("Update thành công").build();
    }
}