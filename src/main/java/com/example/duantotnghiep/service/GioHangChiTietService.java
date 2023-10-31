package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietService {

    MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong);

    List<GioHangCustom> loadGH(UUID id, Integer pageNumber, Integer pageSize);

    void deleteProductInCart(UUID id);
}
