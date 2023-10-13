package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.KhachHang;
import com.example.duantotnghiep.entity.NhanVien;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.NhanVienRepository;
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

    @Autowired
    private NhanVienRepository accountRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    public UUID taoGioHang(String name) {
        // tìm theo username
        NhanVien nhanVien = accountRepository.findByNhanVien(name).get();
        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);
        gioHang.setNhanVien(nhanVien);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

    @Override
    public MessageResponse updateGioHang(UUID idGioHang, UUID idKhachHang) {
        var gioHang = gioHangRepository.findById(idGioHang);
        var khachHang = khachHangRepository.findById(idKhachHang);
        gioHang.get().setKhachHang(khachHang.get());
        gioHangRepository.save(gioHang.get());
        return MessageResponse.builder().message("Update thành công").build();
    }

}


