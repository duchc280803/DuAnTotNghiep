package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public ProductDetailResponse detailProduct(String name) {
        return sanPhamRepository.detailProduct(name);
    }

    @Override
    public List<SizeProductDetailResponse> detailSizeProduct(String name) {
        return sanPhamRepository.detailSizeProduct(name);
    }

    @Override
    public QuantityDetailResponse quantityDetailResponse(UUID id) {
        return sanPhamRepository.quantityDetailResponse(id);
    }
}
