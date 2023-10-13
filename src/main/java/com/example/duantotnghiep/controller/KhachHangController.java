package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping("/api/khach-hang/")
public class KhachHangController {

    @Autowired
    private KhachHangImpl khachHangService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(khachHangService.getKhachHang());
    }

    @GetMapping("search")
    public ResponseEntity<KhachHangResponse> search(@RequestParam(name = "key") String key) {
        return new ResponseEntity<>(khachHangService.findByKeyToKhachHang(key), HttpStatus.OK);
    }

    @GetMapping("detail")
    public ResponseEntity<KhachHangResponse> search(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(khachHangService.detailKhachHang(id), HttpStatus.OK);
    }

    @GetMapping("search_khach_byid")
    public ResponseEntity<KhachHangResponse> findByKhachHangByIdHoaDon(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(khachHangService.findByKhachHangByIdHoaDon(id), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createKhachHang(@RequestBody CreateKhachRequest createKhachRequest) {
        return new ResponseEntity<>(khachHangService.createKhachHang(createKhachRequest), HttpStatus.CREATED);
    }

    @PutMapping("update-hoa-don")
    public ResponseEntity<MessageResponse> updateHoaDon(@RequestParam(name = "id") UUID id,
                                                        @RequestParam(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(khachHangService.updateHoaDon(id, idHoaDon), HttpStatus.OK);
    }

}
