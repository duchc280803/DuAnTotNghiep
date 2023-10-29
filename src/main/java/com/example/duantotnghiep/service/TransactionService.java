package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    MessageResponse createTransaction(UUID idHoaDon, String name,Integer phuongThuc, TransactionRequest transactionRequest);

    List<TransactionResponse> findAllTran(String name);
}
