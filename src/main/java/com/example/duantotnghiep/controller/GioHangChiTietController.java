package com.example.duantotnghiep.controller;
import com.example.duantotnghiep.service.impl.GioHangChiTietServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@RestController
@RequestMapping("/api/gio-hang-chi-tiet")
public class GioHangChiTietController {

    @Autowired
    private GioHangChiTietServiceImpl gioHangChiTietService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> show(){
        return ResponseEntity.ok(gioHangChiTietService.loadGH());
    }

    @PostMapping("/them-san-pham")
    public ResponseEntity<String> themSanPhamVaoGioHangChiTiet(@RequestParam UUID idGioHang, @RequestParam UUID idSanPhamChiTiet, @RequestParam int soLuong) {
        try {
            gioHangChiTietService.themSanPhamVaoGioHangChiTiet(idGioHang, idSanPhamChiTiet, soLuong);
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng chi tiết.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Không thể thêm sản phẩm vào giỏ hàng chi tiết.");
        }
    }
}
