package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.mapper.TaiKhoanMap;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhachHangImpl implements KhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Override
    public List<TaiKhoanMap> getKhachHang() {
        return khachHangRepository.findlistKhachHang();
    }
}
