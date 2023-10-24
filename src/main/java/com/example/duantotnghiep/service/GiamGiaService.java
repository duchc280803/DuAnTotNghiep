package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface GiamGiaService {
    List<GiamGiaResponse> getAll();
    List<ProductDetailResponse> getAllProduct();
    List<GiamGiaResponse> findbyValueString(String key);
    List<ProductDetailResponse> findbyProduct(String key);
    List<GiamGiaResponse>  findbyValueDate(Date key1,Date key2);
    List<GiamGiaResponse> findbyValueStatus(Integer key);
    List<GiamGiaResponse> checkAndSetStatus();
    List<ProductDetailResponse> ListSearch(UUID id);
    MessageResponse createGiamGia(GiamGiaRequest createKhachRequest);
}
