package com.example.duantotnghiep.service.account_service;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface NhanVienCustomService {
    List<NhanVienDTOReponse> getAllNhanVien(String maTaiKhoan, String name, String soDienThoai, Integer trangThai, Integer pageNumber, Integer pageSize);

    TaiKhoan getById(UUID id);

    NhanVienDTOReponse getNhanVienById(UUID id);

    MessageResponse create(MultipartFile file, NhanVienDTORequest request, boolean sendEmail);

    MessageResponse update(UUID id, NhanVienDTORequest request);

    MessageResponse delete(UUID id);

}
