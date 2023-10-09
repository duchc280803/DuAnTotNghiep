package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.service.GioHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangService gioHangService;

    //TODO tạo mới 1 giỏ hàng
    @PostMapping("/tao-gio-hang")
        public ResponseEntity<UUID> taoGioHang(Principal principal) {
        try {
            UUID gioHangId = gioHangService.taoGioHang(principal.getName());
            return ResponseEntity.ok(gioHangId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}