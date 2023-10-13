package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.UUID;

public interface GioHangService {
    UUID taoGioHang(UUID id);

    MessageResponse updateGioHang(UUID idGioHang, UUID idAccount);
}
