package com.example.duantotnghiep.service.impl;


import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.repository.MauSacRepository;
import com.example.duantotnghiep.service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    MauSacRepository repository;


    @Override
    public List<MauSac> getALL() {
        return repository.findAll();
    }
}
