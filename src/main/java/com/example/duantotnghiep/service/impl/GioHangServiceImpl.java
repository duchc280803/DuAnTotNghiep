package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
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
    private AccountRepository accountRepository;


    public UUID taoGioHang(String name) {
        // tìm theo username
        Optional<TaiKhoan> taiKhoan = accountRepository.findByUsername(name);

        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setTaiKhoan(taiKhoan.get());
        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);

        return gioHangMoi.getId();
    }
}


