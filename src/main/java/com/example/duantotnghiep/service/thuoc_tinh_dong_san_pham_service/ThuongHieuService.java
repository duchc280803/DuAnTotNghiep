package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface ThuongHieuService {

    List<ThuongHieu> getAll();

    List<ThuongHieu> getAllThuongHieu(Integer trangThai, String tenThuongHieu, Integer pageNumber, Integer pageSize);

    ThuongHieu getById(UUID id);

    MessageResponse create(ThuongHieuRequest thuongHieu);

    MessageResponse update(UUID id, ThuongHieuRequest thuongHieu);

    MessageResponse delete(UUID id);
}
