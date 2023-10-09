package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.response.KhachHangResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface KhachHangService {
    List<KhachHangMap> getKhachHang();

    TaiKhoan save(TaiKhoan taiKhoan);

    KhachHangResponse findByKhachHang(UUID id);

    KhachHangResponse findByKeyToKhachHang(String key);
}
