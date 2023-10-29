package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.impl.GioHangChiTietServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang-chi-tiet")
public class GioHangChiTietController {


    @Autowired
    private GioHangChiTietServiceImpl gioHangChiTietService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<GioHangCustom>> show(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(gioHangChiTietService.loadGH(name));
    }

    @PostMapping("/them-san-pham")
    public ResponseEntity<MessageResponse> themSanPhamVaoGioHangChiTiet(
            @RequestParam(name = "idGioHang") UUID idGioHang,
            @RequestParam(name = "idSanPhamChiTiet") UUID idSanPhamChiTiet,
            @RequestParam(name = "soLuong") int soLuong) {
        return new ResponseEntity<>(gioHangChiTietService.themSanPhamVaoGioHangChiTiet(idGioHang, idSanPhamChiTiet, soLuong), HttpStatus.CREATED);
    }

    @PutMapping("/update-quantity")
    public ResponseEntity<String> capNhatSoLuong(
            @RequestParam(name = "idgiohangchitiet") UUID idgiohangchitiet,
            @RequestParam(name = "quantity") Integer quantity) {
        try {
            gioHangChiTietService.capNhatSoLuong(idgiohangchitiet, quantity);
            return ResponseEntity.ok("Số lượng đã được cập nhật.(-> nên xem lại Console log)");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }
}
