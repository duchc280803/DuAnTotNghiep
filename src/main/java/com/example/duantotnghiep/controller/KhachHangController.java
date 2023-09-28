package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/khach-hang/")
public class KhachHangController {
    @Autowired
    private KhachHangImpl khachHangService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(khachHangService.getKhachHang());
    }
}
