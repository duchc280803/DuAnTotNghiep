package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ThuongHieuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/thuong-hieu/")
public class ThuongHieuController {

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<ThuongHieu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(thuongHieuService.getAll(), HttpStatus.OK);
    }
    @GetMapping("hien-thi/{id}")
    public ThuongHieu getThuongHieuById(@PathVariable UUID id) {
        return thuongHieuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createThuongHieu(@RequestBody ThuongHieuRequest thuongHieuRequest) {
        return new ResponseEntity<>(thuongHieuService.create(thuongHieuRequest), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateThuongHieu(@PathVariable UUID id, @RequestBody ThuongHieuRequest thuongHieuRequest) {
        try {
            MessageResponse response = thuongHieuService.update(id, thuongHieuRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteThuongHieu(@PathVariable UUID id) {
        return new ResponseEntity<>(thuongHieuService.delete(id), HttpStatus.OK);
    }
}
