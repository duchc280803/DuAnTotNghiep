package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.service.impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/size/")
public class SizeController {

    @Autowired
    private SizeServiceImpl sizeService;

    @GetMapping("show")
    public ResponseEntity<List<Size>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(sizeService.getAll(), HttpStatus.OK);
    }

}
