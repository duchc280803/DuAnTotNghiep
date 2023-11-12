package com.example.duantotnghiep.service.account_service;

import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.UUID;

public interface NhanVienCustomService {

    MessageResponse create(NhanVienDTORequest request);

    MessageResponse update(UUID id, NhanVienDTORequest request);

    MessageResponse delete(UUID id);

}
