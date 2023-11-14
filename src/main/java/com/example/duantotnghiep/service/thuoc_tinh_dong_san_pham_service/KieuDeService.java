package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface KieuDeService {

    List<KieuDe> getAll();

    List<KieuDe> getAllKieuDe(Integer trangThai, String tenDe, Integer pageNumber, Integer pageSize);

    KieuDe getById(UUID id);

    MessageResponse create(KieuDeRequest kieuDeRequest);

    MessageResponse update(UUID id, KieuDeRequest kieuDeRequest);

    MessageResponse delete(UUID id);
}
