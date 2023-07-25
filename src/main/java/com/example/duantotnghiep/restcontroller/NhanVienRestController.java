package com.example.duantotnghiep.restcontroller;

import com.example.duantotnghiep.dto.NhanVienDTO;
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
    public ResponseEntity<NhanVienDTO> createNhanVien(@RequestBody NhanVienDTO nhanVienDTO){
        return nhanVienService.createNhanVien(nhanVienDTO);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<NhanVienDTO> updateNhanVien(@RequestBody NhanVienDTO nhanVienDTO, @PathVariable("id") UUID id){
        return nhanVienService.updateNhanVien(nhanVienDTO,id);
    }
}