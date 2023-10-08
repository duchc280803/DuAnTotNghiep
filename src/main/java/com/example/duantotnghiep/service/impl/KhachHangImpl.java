package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
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
    public List<KhachHangMap> getKhachHang() {
        return khachHangRepository.findlistKhachHang();
    }

    @Override
    public TaiKhoan save(TaiKhoan taiKhoan) {
        return khachHangRepository.save(taiKhoan);
    }

}
