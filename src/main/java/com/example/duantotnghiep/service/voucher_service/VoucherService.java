package com.example.duantotnghiep.service.voucher_service;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface VoucherService {
    List<Voucher> getAll();

    MessageResponse createVoucher(VoucherRequest createVoucherRequest);

    MessageResponse updateVoucher(UUID id, VoucherRequest createVoucherRequest);

    public Voucher findById(UUID id);
    List<Voucher> searchByTrangThai(Integer trangThai);
    public List<Voucher> searchByTenOrMaVoucher(String keyword);
}
