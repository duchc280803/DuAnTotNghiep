package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import com.example.duantotnghiep.service.SanPhamGiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SanPhamGiamGiaServiceImpl implements SanPhamGiamGiaService {

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public List<SanPhamGiamGiaResponse> getAllSpGiamGia() {
        return spGiamGiaRepository.getAllSpGiamGia();
    }
}
