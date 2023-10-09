package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;

import java.util.List;
import java.util.UUID;

public interface GetTongTienService {
    List<TongTienCustom> getTongTien(UUID idgh);
}
