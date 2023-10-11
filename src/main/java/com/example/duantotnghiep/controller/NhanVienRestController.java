package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.response.NhanVienResponse;
import com.example.duantotnghiep.service.impl.NhanVienServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/nhan-vien/")
public class NhanVienRestController {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @GetMapping("all/{username}")
    public ResponseEntity<NhanVienResponse> getAll(@PathVariable("username") String username) {
        return new ResponseEntity<>(nhanVienService.nhanVien(username), HttpStatus.OK);
    }

}