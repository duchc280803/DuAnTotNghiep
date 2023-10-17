package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.repository.ChatLieuRepository;
import com.example.duantotnghiep.service.impl.ChatLieuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/chat-lieu/")
public class ChatLieuController {

    @Autowired
    private ChatLieuServiceImpl chatLieuService;

    @GetMapping("show")
    public ResponseEntity<List<ChatLieu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(chatLieuService.getAll(), HttpStatus.OK);
    }
}
