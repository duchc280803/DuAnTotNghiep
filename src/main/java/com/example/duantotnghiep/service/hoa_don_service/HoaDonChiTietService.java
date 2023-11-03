package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.response.HinhThucThanhToanResponse;
import com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);

    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon);

    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);
}
