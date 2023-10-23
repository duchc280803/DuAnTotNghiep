package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.Date;
import java.util.List;

public interface GiamGiaService {
    List<GiamGiaResponse> getAll();
    List<GiamGiaResponse> findbyValueString(String key);
    List<GiamGiaResponse>  findbyValueDate(Date key1,Date key2);
    List<GiamGiaResponse> findbyValueStatus(Integer key);
    List<GiamGiaResponse> checkAndSetStatus();
    MessageResponse createGiamGia(GiamGiaRequest createKhachRequest);
}
