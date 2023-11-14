package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.VoucherCounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher-counter/")
public class VoucherCounterController {

    @Autowired
    private VoucherCounterServiceImpl voucherCounterService;

    @GetMapping("show")
    public ResponseEntity<List<VoucherCounterResponse>> viewHoaDonTaiQuay(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) {
        if (pageNumber <= 0) {
            pageNumber = 0;
        }
        return new ResponseEntity<>(voucherCounterService.findAll(pageNumber, pageSize), HttpStatus.OK);
    }
}
