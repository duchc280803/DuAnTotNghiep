package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.entity.TaiKhoan;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface NhanVienService {

    ResponseEntity getAll();

    ResponseEntity<NhanVienRequest> createNhanVien(NhanVienRequest NhanVienRequest);

    ResponseEntity<NhanVienRequest> updateNhanVien(NhanVienRequest NhanVienRequest, UUID id);

    TaiKhoan findByNameOrEmail(String name, String email);

}
