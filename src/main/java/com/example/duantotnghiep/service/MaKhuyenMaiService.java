package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.request.CreateMaGiamGiaCreateRequest;
import com.example.duantotnghiep.request.CreateSpGiamGiaRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;

import java.util.List;
import java.util.UUID;

public interface MaKhuyenMaiService {

    List<GiamGiaRequest> listGiamGia(Integer pageNumber, Integer pageSize);

    List<ProductDetailResponse> listProductDetailResponse();

    MessageResponse createDiscountAndAssociatedProducts(CreateMaGiamGiaCreateRequest createMaGiamGiaCreateRequest);

}
