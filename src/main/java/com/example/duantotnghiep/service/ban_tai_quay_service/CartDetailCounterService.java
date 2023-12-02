package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.response.MessageResponse;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CartDetailCounterService {

    MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong, String username) throws IOException, CsvValidationException;

    MessageResponse themSanPhamVaoGioHangChiTietQrCode(UUID idGioHang, String qrCode, String username) throws IOException, CsvValidationException;

    List<GioHangCustom> loadGH(UUID id, Integer pageNumber, Integer pageSize);

    void deleteProductInCart(UUID id);

    List<GioHangChiTiet>  getIdCartDetail(UUID idCart);
}
