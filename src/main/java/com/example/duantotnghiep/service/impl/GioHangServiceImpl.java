package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.repository.NhanVienRepository;
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
    private NhanVienRepository accountRepository;

    public UUID taoGioHang(String name) {
        // tìm theo username

        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

}


