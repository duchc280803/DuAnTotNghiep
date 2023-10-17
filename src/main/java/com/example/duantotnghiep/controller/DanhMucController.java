package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.service.DanhMucService;
import com.example.duantotnghiep.service.impl.DanhMucServiceImpl;
import com.example.duantotnghiep.service.impl.SizeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/danh-muc/")
public class DanhMucController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @GetMapping("show")
    public ResponseEntity<List<DanhMuc>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(danhMucService.getAll(), HttpStatus.OK);
    }
}
