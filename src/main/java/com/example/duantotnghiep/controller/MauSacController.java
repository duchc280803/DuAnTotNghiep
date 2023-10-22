package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.service.impl.MauSacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/mau-sac/")
public class MauSacController {

    @Autowired
    private MauSacServiceImpl mauSacService;

    @GetMapping("show")
    public ResponseEntity<List<MauSac>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(mauSacService.getAll(), HttpStatus.OK);
    }
}
