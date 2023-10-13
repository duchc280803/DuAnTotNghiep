package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.service.GetSoLuongGioHangService;
import com.example.duantotnghiep.service.GetTongTienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TongTienServiceImpl implements GetTongTienService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<TongTienCustom> getTongTien(UUID idgh) {
        return sanPhamRepository.getTongTien(idgh);
    }
}


