package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface HoaDonChiTietService {

    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);

    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon);

    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);

    List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(UUID idHoaDon);

    void confirmThanhToan(UUID hoadonId, XacNhanThanhToanRequest request);

    MoneyResponse getMoneyByHoaDon(UUID idHoaDon);

    MessageResponse themSanPhamVaoHoaDonChiTiet(UUID idHoaDon, UUID idSanPhamChiTiet, int soLuong);

    void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi);

    MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest);

    HoaDon findByHoaDon(UUID id);

}
