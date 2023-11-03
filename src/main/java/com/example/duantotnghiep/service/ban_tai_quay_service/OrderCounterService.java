package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.MessageResponse;

import com.example.duantotnghiep.response.OrderCounterCartsResponse;


import java.util.List;
import java.util.UUID;

public interface OrderCounterService {

    MessageResponse taoHoaDon(String name);

    List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize);

    List<HoaDonResponse> findByCodeOrder(String ma);

    MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest);

    OrderCounterCartsResponse findByHoaDon(UUID id);

    IdGioHangResponse showIdGioHangCt(UUID id);

}
