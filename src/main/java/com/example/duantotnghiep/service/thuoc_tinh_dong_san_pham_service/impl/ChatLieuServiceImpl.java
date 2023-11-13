package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.repository.ChatLieuRepository;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    private ChatLieuRepository chatLieuRepository;

    private Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    @Override
    public List<ChatLieu> getAll() {
        return chatLieuRepository.findByTrangThai(1);
    }

    @Override
    public List<ChatLieu> getAllChatLieu(Integer trangThai, String tenChatLieu, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ChatLieu> pageList = chatLieuRepository.getAllChatLieu(trangThai, tenChatLieu, pageable);
        return pageList.getContent();
    }

    @Override
    public ChatLieu getById(UUID id) {
        return chatLieuRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(ChatLieuRequest request) {
        ChatLieu chatLieu = new ChatLieu();
        chatLieu.setId(UUID.randomUUID());
        chatLieu.setTenChatLieu(request.getTenChatLieu());
        chatLieu.setTrangThai(request.getTrangThai());
        chatLieu.setNgayTao(timestamp);
        chatLieuRepository.save(chatLieu);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, ChatLieuRequest request) {
        Optional<ChatLieu> chatLieuOptional= chatLieuRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            ChatLieu chatLieu = chatLieuOptional.get();
            chatLieu.setTenChatLieu(request.getTenChatLieu());
            chatLieu.setTrangThai(request.getTrangThai());
            chatLieu.setNgayCapNhat(timestamp);
            chatLieuRepository.save(chatLieu);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<ChatLieu> chatLieuOptional= chatLieuRepository.findById(id);
        if (chatLieuOptional.isPresent()) {
            ChatLieu chatLieu = chatLieuOptional.get();
            chatLieu.setTrangThai(2);
            chatLieu.setNgayCapNhat(timestamp);
            chatLieuRepository.save(chatLieu);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }



}
