package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface VoucherCounterService {

    List<VoucherCounterResponse> findAll(Integer pageNumber, Integer pageSize);

    MessageResponse addVoucherOrder(UUID idHoaDon, UUID idVoucher, BigDecimal thanhTien);

    String findByName(UUID id);
}
