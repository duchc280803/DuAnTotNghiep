package com.example.duantotnghiep.service;

import com.example.duantotnghiep.response.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductDetailResponse detailProduct(String name);

    List<SizeProductDetailResponse> detailSizeProduct(String name);

    QuantityDetailResponse quantityDetailResponse(UUID id);
}
