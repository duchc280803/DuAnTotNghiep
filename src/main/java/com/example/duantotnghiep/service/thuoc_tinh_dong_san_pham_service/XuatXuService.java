package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface XuatXuService {

    List<XuatXu> getAll();

    List<XuatXu> getAllXuatXu(Integer trangThai, String tenXuatXu, Integer pageNumber, Integer pageSize);

    XuatXu getById(UUID id);

    MessageResponse create(XuatXuRequest xuatXuRequest);

    MessageResponse update(UUID id, XuatXuRequest xuatXuRequest);

    MessageResponse delete(UUID id);
}
