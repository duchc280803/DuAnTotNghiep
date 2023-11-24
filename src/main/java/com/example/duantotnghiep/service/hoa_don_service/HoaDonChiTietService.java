package com.example.duantotnghiep.service.hoa_don_service;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.request.TraHangRequest;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
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

    MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws IOException, CsvValidationException;

    HoaDon findByHoaDon(UUID id);

    ProductDetailDTOResponse getDetailSanPham(UUID idhdct);

    MessageResponse createOrUpdate(UUID idhdct, TraHangRequest traHangResponse, String username) throws IOException, CsvValidationException;

    void deleteOrderDetail(UUID id);

    boolean traHang(UUID id);

    OrderDetailUpdate orderDetailUpdate(UUID id);

}
