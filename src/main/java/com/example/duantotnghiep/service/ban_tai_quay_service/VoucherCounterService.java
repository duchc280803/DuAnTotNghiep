package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.response.VoucherCounterResponse;

public interface VoucherCounterService {

    VoucherCounterResponse findAll(Integer pageNumber, Integer pageSize);
}
