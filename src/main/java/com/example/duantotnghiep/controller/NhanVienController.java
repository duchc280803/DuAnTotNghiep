package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.NhanVien;
import com.example.duantotnghiep.service.impl.NhanVienServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/nhan-vien/")
public class NhanVienController {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @GetMapping("find-name")
    public ResponseEntity<NhanVien> findNhanVien(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(nhanVienService.nhanVien(name), HttpStatus.OK);
    }
}
