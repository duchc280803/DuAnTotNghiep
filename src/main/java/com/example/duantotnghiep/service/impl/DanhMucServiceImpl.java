package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.repository.DanhMucRepository;
import com.example.duantotnghiep.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    DanhMucRepository repository;
    @Override
    public List<DanhMuc> getAll() {
        return repository.findAll();
    }
}
