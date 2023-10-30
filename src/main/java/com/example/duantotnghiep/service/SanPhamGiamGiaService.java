package com.example.duantotnghiep.service;

import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;

import java.util.List;

public interface SanPhamGiamGiaService {

    List<SanPhamGiamGiaResponse> getAllSpGiamGia(String tenSanPham);
}
