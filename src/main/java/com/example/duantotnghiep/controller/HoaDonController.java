package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.impl.HoaDonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hoa-don/")
public class HoaDonController {

    @Autowired
    private HoaDonServiceImpl hoaDonService;

    @GetMapping("show")
    public ResponseEntity<List<HoaDonResponse>> viewHoaDonTaiQuay() {
        return new ResponseEntity<>(hoaDonService.viewHoaDonTaiQuay(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> taoHoaDon(Principal principal) {
        return new ResponseEntity<>(hoaDonService.taoHoaDon(principal.getName()), HttpStatus.CREATED);
    }

    @PostMapping("create-hoa-don-chi-tiet")
    public ResponseEntity<MessageResponse> taoHoaDonDetial(@RequestBody HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        return new ResponseEntity<>(hoaDonService.updateHoaDon(hoaDonThanhToanRequest), HttpStatus.CREATED);
    }
}
