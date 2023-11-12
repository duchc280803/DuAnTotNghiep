package com.example.duantotnghiep.service.account_service;

import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;

import java.util.List;
import java.util.UUID;

public interface NhanVienCustomService {
    List<NhanVienDTOReponse> getAllNhanVien(String maTaiKhoan, Integer trangThai, Integer pageNumber, Integer pageSize);

    MessageResponse create(NhanVienDTORequest request);

    MessageResponse update(UUID id, NhanVienDTORequest request);

    MessageResponse delete(UUID id);

}
