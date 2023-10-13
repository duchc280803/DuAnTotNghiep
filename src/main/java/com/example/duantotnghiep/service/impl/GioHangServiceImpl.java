package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangServiceImpl implements GioHangService {
    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private AccountRepository accountRepository;

    public UUID taoGioHang(UUID id) {
        // tìm theo username
        Optional<TaiKhoan> taiKhoan = accountRepository.findByKhachHang(id);

        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());

        gioHang.setTaiKhoan(taiKhoan.get());
        gioHang.setNgayTao(new Date(System.currentTimeMillis()));
        gioHang.setTrangThai(1);

        GioHang gioHangMoi = gioHangRepository.save(gioHang);
        return gioHangMoi.getId();
    }

    @Override
    public MessageResponse updateGioHang(UUID idGioHang, UUID idAccount) {
        var gioHang = gioHangRepository.findById(idGioHang);
        var khachHang = accountRepository.findById(idAccount);
        gioHang.get().setTaiKhoan(khachHang.get());
        gioHangRepository.save(gioHang.get());
        return MessageResponse.builder().message("Update thành công").build();
    }
}