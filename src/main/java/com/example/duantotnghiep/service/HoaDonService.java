package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;

public interface HoaDonService {

    MessageResponse taoHoaDon(String name);

    List<HoaDonResponse> viewHoaDonTaiQuay();

    MessageResponse updateHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest);
}
