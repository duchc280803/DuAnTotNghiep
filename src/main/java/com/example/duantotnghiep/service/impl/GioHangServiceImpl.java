package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.UUID;

@Service
public class GioHangServiceImpl implements GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public UUID taoGioHang(String name) {
        // tìm theo username
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(name).get();
        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);
        gioHang.setTaiKhoan(taiKhoan);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

    @Override
    public MessageResponse updateGioHang(UUID idGioHang, UUID idKhachHang) {
        var gioHang = gioHangRepository.findById(idGioHang);
        var taiKhoan = taiKhoanRepository.findById(idKhachHang);
        gioHang.get().setTaiKhoan(taiKhoan.get());
        gioHangRepository.save(gioHang.get());
        return MessageResponse.builder().message("Update thành công").build();
    }

}


