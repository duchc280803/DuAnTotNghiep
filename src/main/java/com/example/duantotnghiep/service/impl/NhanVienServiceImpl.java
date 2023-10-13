package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.response.NhanVienResponse;
import com.example.duantotnghiep.service.NhanVienService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NhanVienServiceImpl implements NhanVienService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<NhanVienResponse> getAllPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<NhanVienResponse> pageList = accountRepository.getAllPage(pageable);
        return pageList.getContent();
    }

    @Override
    public NhanVienResponse getList(String name) {
        return accountRepository.getList(name);
    }

}
