package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.repository.SizeRepository;
import com.example.duantotnghiep.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public List<Size> getAll() {
        return sizeRepository.findAll();
    }
}
