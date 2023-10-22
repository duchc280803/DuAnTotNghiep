package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.ChatLieu;

import java.util.List;

public interface ChatLieuService {

    List<ChatLieu> getAll();
    void saveAll(List<ChatLieu> entities);
}
