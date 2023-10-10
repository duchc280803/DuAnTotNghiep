package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.response.KhachHangResponse;

import java.util.List;
import java.util.UUID;

public interface KhachHangService {
    List<KhachHangMap> getKhachHang();

    KhachHangResponse findByKhachHang(UUID id);

    KhachHangResponse findByKeyToKhachHang(String key);

}
