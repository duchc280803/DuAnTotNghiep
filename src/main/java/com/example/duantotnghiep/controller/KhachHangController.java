package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/khach-hang/")
public class KhachHangController {

    @Autowired
    private KhachHangImpl khachHangService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(khachHangService.getKhachHang());
    }

    @GetMapping("detail")
    public ResponseEntity<KhachHangResponse> getDetailId(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(khachHangService.findByKhachHang(id), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<KhachHangResponse> search(@RequestParam(name = "key") String key) {
        return new ResponseEntity<>(khachHangService.findByKeyToKhachHang(key), HttpStatus.OK);
    }

}
