package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.VoucherCounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateHoaDon(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "idVoucher") UUID idVoucher,
            @RequestParam(name = "thanhTien") BigDecimal thanhTien) {
        return new ResponseEntity<>(voucherCounterService.addVoucherOrder(idHoaDon, idVoucher, thanhTien), HttpStatus.OK);
    }

    @GetMapping("name")
    public ResponseEntity<Map<String, String>> findByName(@RequestParam(name = "id") UUID id) {
        Map<String, String> responseData = new HashMap<>();
        responseData.put("voucherName", voucherCounterService.findByName(id));
        return ResponseEntity.ok(responseData);
    }


}
