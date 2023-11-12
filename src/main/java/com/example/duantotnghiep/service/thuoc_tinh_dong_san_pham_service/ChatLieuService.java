package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;
import java.util.UUID;

public interface ChatLieuService {
    List<ChatLieu> getAll();

    ChatLieu getById(UUID id);

    MessageResponse create(ChatLieuRequest request);

    MessageResponse update(UUID id, ChatLieuRequest request);

    MessageResponse delete(UUID id);
}