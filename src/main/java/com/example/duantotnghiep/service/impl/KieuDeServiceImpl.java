package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.repository.KieuDeRepository;
import com.example.duantotnghiep.service.KieuDeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KieuDeServiceImpl implements KieuDeService {


    @Autowired
    KieuDeRepository repository;


    @Override
    public List<KieuDe> getALL() {
        return repository.findAll();
    }
}
