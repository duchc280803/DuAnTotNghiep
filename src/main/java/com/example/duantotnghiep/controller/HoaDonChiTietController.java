package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;
import com.example.duantotnghiep.service.impl.HoaDonServiceImpl;
import com.example.duantotnghiep.service.impl.TrangThaiHoaDonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hoa-don-chi-tiet/")
public class HoaDonChiTietController {
    @Autowired
    private HoaDonServiceImpl hoaDonService;

    @Autowired
    private TrangThaiHoaDonServiceImpl trangThaiHoaDonService;

    @GetMapping("hien-thi")
    public ResponseEntity<ThongTinDonHang> viewThongTinDonHang(@RequestParam("idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonService.getThongTinDonHang(idHoaDon), HttpStatus.OK);
    }
    @PutMapping("/hien-thi/{hoadonId}")
    public ResponseEntity<?> updateTrangThaiHoaDon(@PathVariable UUID hoadonId, @RequestBody Integer newTrangThai, @RequestBody String ghiChu) {
        trangThaiHoaDonService.updateTrangThaiHoaDon(hoadonId, newTrangThai,ghiChu);
        return ResponseEntity.ok("Trạng thái hóa đơn đã được cập nhật.");
    }
}
