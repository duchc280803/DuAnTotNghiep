package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.service.GetSoLuongGioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
