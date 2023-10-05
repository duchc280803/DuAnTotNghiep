package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.GioHangCustom;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietService {
    void themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong);
    List<GioHangCustom> loadGH();
}
