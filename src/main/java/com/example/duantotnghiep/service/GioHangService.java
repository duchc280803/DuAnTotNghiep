package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.response.MessageResponse;
import org.hibernate.sql.Update;

import java.util.UUID;

public interface GioHangService {
    UUID taoGioHang(String name);

    MessageResponse updateGioHang(UUID idGioHang, UUID idKhachHang);

}
