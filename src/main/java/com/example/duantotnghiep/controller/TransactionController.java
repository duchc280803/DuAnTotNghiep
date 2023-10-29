package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;
import com.example.duantotnghiep.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/transaction/")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionService;

    @GetMapping("show")
    public ResponseEntity<List<TransactionResponse>> findAllTran(@RequestParam(name = "name") String name) {
        return new ResponseEntity<>(transactionService.findAllTran(name), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createTransaction(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "name") String name,
            @RequestParam(name = "phuongThuc") Integer phuongThuc,
            @RequestBody TransactionRequest transactionRequest
    ) {
        return new ResponseEntity<>(transactionService.createTransaction(idHoaDon, name, phuongThuc, transactionRequest), HttpStatus.CREATED);
    }
}
