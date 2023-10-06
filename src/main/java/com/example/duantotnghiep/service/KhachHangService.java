package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;

import java.util.List;

public interface KhachHangService {
    List<KhachHangMap> getKhachHang();
    TaiKhoan save(TaiKhoan taiKhoan);
}
