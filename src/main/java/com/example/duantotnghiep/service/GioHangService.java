package com.example.duantotnghiep.service;

import com.example.duantotnghiep.response.MessageResponse;

import java.util.UUID;

public interface GioHangService {

    MessageResponse updateGioHang(UUID idGioHang, UUID idAccount);
}
