package com.example.duantotnghiep.controller;
import com.example.duantotnghiep.service.impl.XuatSuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin("*")
@RequestMapping("/api/xuat-su")
public class XuatXuController {


    @Autowired
    XuatSuServiceImpl service;

    @GetMapping("/show")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(service.getALL());
    }
}
