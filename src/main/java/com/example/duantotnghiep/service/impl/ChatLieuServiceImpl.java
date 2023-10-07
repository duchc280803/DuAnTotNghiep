package com.example.duantotnghiep.service.impl;


import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.repository.ChatLieuRepository;
import com.example.duantotnghiep.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatLieuServiceImpl implements ChatLieuService {

    @Autowired
    ChatLieuRepository repository;


    @Override
    public List<ChatLieu> getALL() {
        return repository.findAll();
    }
}
