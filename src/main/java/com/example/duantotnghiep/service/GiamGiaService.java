package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.response.GiamGiaResponse;

import java.util.Date;
import java.util.List;

public interface GiamGiaService {
    List<GiamGiaResponse> getAll();
GiamGiaResponse findbyValueString(String key);
    List<GiamGiaResponse>  findbyValueDate(Date key1,Date key2);
    List<GiamGiaResponse> findbyValueStatus(Integer key);
    List<GiamGiaResponse> checkAndSetStatus();
}
