package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;

import java.util.List;
import java.util.UUID;

public interface GetSoLuongGioHangService {
    List<SoLuongGioHangCustom> getSoLuongGioHang(UUID idgh);
}
