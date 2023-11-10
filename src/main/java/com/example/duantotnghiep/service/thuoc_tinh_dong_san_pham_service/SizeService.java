package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.request.SizeRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface SizeService {

    List<Size> getAll();

    Size getById(UUID id);

    MessageResponse create(SizeRequest sizeRequest);

    MessageResponse update(UUID id, SizeRequest sizeRequest);

    MessageResponse delete(UUID id);
}
