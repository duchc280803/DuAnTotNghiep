package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.repository.NhanVienRepository;
import com.example.duantotnghiep.response.NhanVienResponse;
import com.example.duantotnghiep.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private NhanVienRepository accountRepository;

}
