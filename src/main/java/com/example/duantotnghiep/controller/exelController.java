package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.schedulingtasks.ExcelReader;
import com.example.duantotnghiep.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/exel/")
public class exelController {
    @Autowired
    private ChatLieuService service;

    @Autowired
    private ExcelReader excelReader;

    @PostMapping("/uploadExcel")
    public ResponseEntity<String> uploadExcel(@RequestParam("file") MultipartFile file) {
        List<ChatLieu> entities = excelReader.readExcel(file);
        service.saveAll(entities);

        return ResponseEntity.ok("Data imported successfully.");
    }
}
