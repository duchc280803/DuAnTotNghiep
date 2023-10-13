package com.example.duantotnghiep.controller;


import com.example.duantotnghiep.service.impl.DanhMucServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/danh-muc")
public class DanhMucController {

    @Autowired
    DanhMucServiceImpl service;

    @GetMapping("/show")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getAll());
    }

}