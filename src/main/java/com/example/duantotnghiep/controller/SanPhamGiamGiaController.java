package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import com.example.duantotnghiep.service.impl.SanPhamGiamGiaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/san-pham-giam-gia/")
public class SanPhamGiamGiaController {

    @Autowired
    private SanPhamGiamGiaServiceImpl sanPhamGiamGiaService;

    @GetMapping("show")
    public ResponseEntity<List<SanPhamGiamGiaResponse>> getAllSanPhamGiamGia(@RequestParam(name = "tenSanPham", required = false) String tenSanPham) {
        return new ResponseEntity<>(sanPhamGiamGiaService.getAllSpGiamGia(tenSanPham), HttpStatus.OK);
    }
}
