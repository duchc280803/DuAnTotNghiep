package com.example.duantotnghiep.service;

import com.example.duantotnghiep.response.HinhThucThanhToanResponse;
import com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;

import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {
<<<<<<< HEAD

=======
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);

    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon);

    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);
}
