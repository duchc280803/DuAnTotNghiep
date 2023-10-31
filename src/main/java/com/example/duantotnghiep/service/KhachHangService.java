package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface KhachHangService {

    List<KhachHangResponse> getKhachHang();

    List<KhachHangResponse> findByKeyToKhachHang(String key);

    MessageResponse createKhachHang(CreateKhachRequest createKhachRequest);

    MessageResponse updateHoaDon(UUID id, UUID idHoaDon);

    KhachHangResponse detailKhachHang(UUID id);

    MessageResponse updateKhachVaoGioHang(UUID id, UUID idGioHang);
}
