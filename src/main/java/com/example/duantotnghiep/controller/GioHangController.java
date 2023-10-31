package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.impl.GioHangServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang")
public class GioHangController {

    @Autowired
    private GioHangServiceImpl gioHangService;

    @PutMapping("/update")
    public ResponseEntity<MessageResponse> updateGioHang(@RequestParam("idGioHang") UUID idGioHang,
                                                         @RequestParam("idKhachHang") UUID idAccount) {
        return new ResponseEntity<>(gioHangService.updateGioHang(idGioHang, idAccount), HttpStatus.OK);
    }

}