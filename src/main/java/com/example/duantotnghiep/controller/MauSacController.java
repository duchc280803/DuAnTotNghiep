package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.service.impl.MauSacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/mau-sac")
public class MauSacController {

    @Autowired
    MauSacServiceImpl service;

    @GetMapping("/show")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getALL());
    }
}
