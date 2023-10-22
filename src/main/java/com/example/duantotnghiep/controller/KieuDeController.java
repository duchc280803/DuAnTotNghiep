package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import com.example.duantotnghiep.service.impl.KieuDeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/kieu-de/")
public class KieuDeController {

    @Autowired
    private KieuDeServiceImpl kieuDeService;

    @GetMapping("show")
    public ResponseEntity<List<KieuDe>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(kieuDeService.getAll(), HttpStatus.OK);
    }
}
