package com.example.duantotnghiep.controller.authentication_controller;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import com.example.duantotnghiep.service.account_service.impl.NhanVienServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/nhan-vien/")
public class NhanVienController {
    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<NhanVienDTOReponse>> getAllSanPhamGiamGia(
            @RequestParam(name = "maNhanVien", required = false) String maNhanVien,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(nhanVienService.getAllNhanVien(maNhanVien, trangThai, pageNumber, pageSize), HttpStatus.OK);
    }


    @PostMapping("create")
    public ResponseEntity<MessageResponse> createThuongHieu(@RequestBody NhanVienDTORequest request) {
        return new ResponseEntity<>(nhanVienService.create(request), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateThuongHieu(@PathVariable UUID id, @RequestBody NhanVienDTORequest request) {
        try {
            MessageResponse response = nhanVienService.update(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteThuongHieu(@PathVariable UUID id) {
        return new ResponseEntity<>(nhanVienService.delete(id), HttpStatus.OK);
    }
}
