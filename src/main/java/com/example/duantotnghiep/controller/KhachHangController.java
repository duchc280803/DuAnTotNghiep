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

@RestController
@RequestMapping("/api/khach-hang/")
public class KhachHangController {

//    @Autowired
//    private KhachHangImpl khachHangService;
//
//    @GetMapping("hien-thi")
//    public ResponseEntity<?> getAll() {
//        return ResponseEntity.ok(khachHangService.getKhachHang());
//    }
//
//    @GetMapping("detail")
//    public ResponseEntity<KhachHangResponse> getDetailId(@RequestParam(name = "id") UUID id,
//                                                         @RequestParam(name = "idHoaDon") UUID idHoaDon) {
//        return new ResponseEntity<>(khachHangService.findByKhachHang(id, idHoaDon), HttpStatus.OK);
//    }
//
//    @GetMapping("search")
//    public ResponseEntity<KhachHangResponse> search(@RequestParam(name = "key") String key) {
//        return new ResponseEntity<>(khachHangService.findByKeyToKhachHang(key), HttpStatus.OK);
//    }
//
//    @PostMapping("create")
//    public ResponseEntity<MessageResponse> createKhachHang(@RequestBody CreateKhachRequest createKhachRequest) {
//        return new ResponseEntity<>(khachHangService.createKhachHang(createKhachRequest), HttpStatus.CREATED);
//    }
//
//    @PutMapping("update-hoa-don")
//    public ResponseEntity<MessageResponse> updateHoaDon(@RequestParam(name = "id") UUID id,
//                                                        @RequestParam(name = "idHoaDon") UUID idHoaDon) {
//        return new ResponseEntity<>(khachHangService.updateHoaDon(id, idHoaDon), HttpStatus.OK);
//    }

}
