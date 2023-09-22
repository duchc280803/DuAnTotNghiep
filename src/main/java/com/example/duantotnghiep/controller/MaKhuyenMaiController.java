package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.impl.MaKhuyenMaiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/khuyen-mai/")
public class MaKhuyenMaiController {

    @Autowired
    private MaKhuyenMaiServiceImpl maKhuyenMaiService;

    @GetMapping("show-product")
    public ResponseEntity<List<ProductDetailResponse>> getAll() {
        return ResponseEntity.ok(maKhuyenMaiService.listProductDetailResponse());
    }
}
