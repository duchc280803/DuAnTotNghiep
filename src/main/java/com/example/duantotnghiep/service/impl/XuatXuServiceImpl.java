package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.repository.XuatSuRepository;
import com.example.duantotnghiep.service.XuatXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class XuatXuServiceImpl implements XuatXuService {

    @Autowired
    private XuatSuRepository xuatSuRepository;

    @Override
    public List<XuatXu> getAll() {
        return xuatSuRepository.findAll();
    }
}
