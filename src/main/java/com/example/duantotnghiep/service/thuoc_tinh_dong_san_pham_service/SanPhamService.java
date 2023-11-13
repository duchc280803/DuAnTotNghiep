package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.response.SanPhamResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface SanPhamService {
    List<SanPhamResponse> getNewProduct();

}
