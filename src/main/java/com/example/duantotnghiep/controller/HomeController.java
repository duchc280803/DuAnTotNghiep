package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.service.impl.DanhMucServiceImpl;
import com.example.duantotnghiep.service.impl.SanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/public/")
public class HomeController {

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @Autowired
    private DanhMucServiceImpl danhMucService;
    @GetMapping("home")
    public ResponseEntity<List<SanPhamResponse>> getAll(){
        return new ResponseEntity<>(sanPhamService.getNewProduct(), HttpStatus.OK);
    }
}
