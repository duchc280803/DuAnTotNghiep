package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.response.VoucherCounterResponse;

import java.util.List;

public interface VoucherCounterService {

    List<VoucherCounterResponse> findAll(Integer pageNumber, Integer pageSize);
}
