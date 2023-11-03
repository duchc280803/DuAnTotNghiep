package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.not_login.loadsanpham_not_login;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;

import java.util.List;

public interface SanPhamGiamGiaService {

    List<loadsanpham_not_login> getAllSpGiamGia();
}
