package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface ChatLieuService {
    List<ChatLieu> getAll();

    List<ChatLieu> getAllChatLieu(Integer trangThai, String tenChatLieu, Integer pageNumber, Integer pageSize);

    ChatLieu getById(UUID id);

    MessageResponse create(ChatLieuRequest request);

    MessageResponse update(UUID id, ChatLieuRequest request);

    MessageResponse delete(UUID id);
}