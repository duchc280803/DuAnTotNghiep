package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.service.impl.NhanVienServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/nhan-vien/")
public class NhanVienRestController {

    @Autowired
    private NhanVienServiceImpl nhanVienService;

    @GetMapping("hien-thi")
    public ResponseEntity getAll(){
        return nhanVienService.getAll();
    }

    @PostMapping("create")
    public ResponseEntity<NhanVienRequest> createNhanVien(@RequestBody NhanVienRequest nhanVienRequest){
        return nhanVienService.createNhanVien(nhanVienRequest);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<NhanVienRequest> updateNhanVien(@RequestBody NhanVienRequest nhanVienRequest, @PathVariable("id") UUID id){
        return nhanVienService.updateNhanVien(nhanVienRequest,id);
    }

    @GetMapping("search")
    public TaiKhoan findByNameOrEmail(@RequestParam("name") String name,@RequestParam("email") String email) {
        return nhanVienService.findByNameOrEmail(name,email);
    }
}