package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.response.NhanVienResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface NhanVienService {

    List<NhanVienResponse> getAllPage(Integer pageNumber, Integer pageSize);

    NhanVienRequest createNhanVien(NhanVienRequest NhanVienRequest);

    NhanVienRequest updateNhanVien(NhanVienRequest NhanVienRequest, UUID id);

}
