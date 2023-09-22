package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.repository.ProductDetailRepository;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.MaKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaKhuyenMaiServiceImpl implements MaKhuyenMaiService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Override
    public List<ProductDetailResponse> listProductDetailResponse() {
        return productDetailRepository.listProductDetailResponse();
    }
}
