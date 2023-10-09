package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.service.GetTongTienService;
import com.example.duantotnghiep.service.GioHangService;
import com.example.duantotnghiep.service.impl.GetSoLuongGioHangServiceImpl;
import com.example.duantotnghiep.service.impl.GioHangServiceImpl;
import com.example.duantotnghiep.service.impl.TongTienServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangServiceImpl gioHangService;

    @Autowired
    private GetSoLuongGioHangServiceImpl getSoLuongGioHangService;

    @Autowired
    private TongTienServiceImpl gettongtien;

    //TO DO tạo mới 1 giỏ hàng
    @PostMapping("/tao-gio-hang")
        public ResponseEntity<UUID> taoGioHang(@RequestParam String name) {
        try {
            UUID gioHangId = gioHangService.taoGioHang(name);
            return ResponseEntity.ok(gioHangId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/so-luong-san-pham")
    public ResponseEntity<?> getSoLuongGioHang(@RequestParam UUID idgh) {
        return  ResponseEntity.ok(getSoLuongGioHangService.getSoLuongGioHang(idgh));
    }
    @GetMapping("/tong-tien-san-pham")
    public ResponseEntity<?> getTongTien(@RequestParam UUID idgh) {
        return  ResponseEntity.ok(gettongtien.getTongTien(idgh));
    }
}