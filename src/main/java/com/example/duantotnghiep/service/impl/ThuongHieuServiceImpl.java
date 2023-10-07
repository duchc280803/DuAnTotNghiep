package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.repository.ThuongHieuRepository;
import com.example.duantotnghiep.service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    ThuongHieuRepository repository;


    @Override
    public List<ThuongHieu> getALL() {
        return repository.findAll();
    }
}
