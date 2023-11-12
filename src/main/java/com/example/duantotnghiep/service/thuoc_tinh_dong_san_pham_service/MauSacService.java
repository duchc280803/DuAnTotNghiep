package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface MauSacService {

    List<MauSac> getAll();

    List<MauSac> getAllMauSac(Integer trangThai, String tenMauSac, Integer pageNumber, Integer pageSize);

    MauSac getById(UUID id);

    MessageResponse create(MauSacRequest mauSacRequest);

    MessageResponse update(UUID id, MauSacRequest mauSacRequest);

    MessageResponse delete(UUID id);
}
