package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface KhachHangService {
    List<KhachHangMap> getKhachHang();

    KhachHangResponse findByKhachHang(UUID id, UUID idHoaDon);

    KhachHangResponse findByKeyToKhachHang(String key);

    MessageResponse createKhachHang(CreateKhachRequest createKhachRequest);

    MessageResponse updateHoaDon(UUID id, UUID idHoaDon);

}
