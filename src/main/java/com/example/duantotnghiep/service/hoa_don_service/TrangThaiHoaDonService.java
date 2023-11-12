package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.UUID;

public interface TrangThaiHoaDonService {

    MessageResponse confirmOrder(UUID hoadonId, TrangThaiHoaDonRequest request, String name);
}