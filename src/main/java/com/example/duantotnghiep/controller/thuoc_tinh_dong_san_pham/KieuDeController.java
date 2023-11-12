package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.KieuDeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/kieu-de/")
public class KieuDeController {

    @Autowired
    private KieuDeServiceImpl kieuDeService;

    @GetMapping("show")
    public ResponseEntity<List<KieuDe>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(kieuDeService.getAll(), HttpStatus.OK);
    }
    @GetMapping("hien-thi/{id}")
    public KieuDe getKieuDeById(@PathVariable UUID id) {
        return kieuDeService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createKieuDe(@RequestBody KieuDeRequest kieuDeRequest) {
        return new ResponseEntity<>(kieuDeService.create(kieuDeRequest), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateKieuDe(@PathVariable UUID id, @RequestBody KieuDeRequest kieuDeRequest) {
        try {
            MessageResponse response = kieuDeService.update(id, kieuDeRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteKieuDe(@PathVariable UUID id) {
        return new ResponseEntity<>(kieuDeService.delete(id), HttpStatus.OK);
    }
}
