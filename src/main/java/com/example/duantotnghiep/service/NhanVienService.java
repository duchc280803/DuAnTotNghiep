package com.example.duantotnghiep.service;

import com.example.duantotnghiep.dto.NhanVienDTO;
import com.example.duantotnghiep.entity.TaiKhoan;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface NhanVienService {

    ResponseEntity getAll();

    ResponseEntity<NhanVienDTO> createNhanVien(NhanVienDTO NhanVienDTO);

    ResponseEntity<NhanVienDTO> updateNhanVien(NhanVienDTO NhanVienDTO, UUID id);

}
