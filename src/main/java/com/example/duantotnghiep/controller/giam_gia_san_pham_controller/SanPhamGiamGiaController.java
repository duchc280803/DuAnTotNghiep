package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import com.example.duantotnghiep.service.giam_gia_service.GiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/san-pham-giam-gia/")
public class SanPhamGiamGiaController {

    @Autowired
    private GiamGiaService.SanPhamGiamGiaServiceImpl sanPhamGiamGiaService;

    @GetMapping("show")
    public ResponseEntity<List<SanPhamGiamGiaResponse>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(sanPhamGiamGiaService.getAllSpGiamGia(), HttpStatus.OK);
    }
}
