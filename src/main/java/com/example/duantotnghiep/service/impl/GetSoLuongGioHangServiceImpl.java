package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.service.GetSoLuongGioHangService;
import com.example.duantotnghiep.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetSoLuongGioHangServiceImpl implements GetSoLuongGioHangService {
    @Autowired
    private SanPhamRepository sanPhamRepository;


    @Override
    public List<SoLuongGioHangCustom> getSoLuongGioHang(UUID idgh) {
        return sanPhamRepository.getSoLuongGioHang(idgh);
    }
}


