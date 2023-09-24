package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.mapper.GiamGiaMapper;
import com.example.duantotnghiep.mapper.ProductMapper;
import com.example.duantotnghiep.repository.GiamGiaRepository;
import com.example.duantotnghiep.repository.ProductDetailRepository;
import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import com.example.duantotnghiep.request.CreateMaGiamGiaCreateRequest;
import com.example.duantotnghiep.request.CreateSpGiamGiaRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.MaKhuyenMaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MaKhuyenMaiServiceImpl implements MaKhuyenMaiService {

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private GiamGiaRepository giamGiaRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public List<GiamGiaRequest> listGiamGia(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SpGiamGia> spGiamGiaPage = spGiamGiaRepository.findAll(pageable);
        List<SpGiamGia> giamGiaRequestList = spGiamGiaPage.getContent();
        List<GiamGiaRequest> result = new ArrayList<>();
        for (SpGiamGia spGiamGia: giamGiaRequestList) {
            GiamGiaRequest giamGiaRequest = GiamGiaMapper.mapToGiamGiaRequest(spGiamGia);
            result.add(giamGiaRequest);
        }
        return result;
    }

    @Override
    public List<ProductDetailResponse> listProductDetailResponse() {
        return productDetailRepository.listProductDetailResponse();
    }

    @Override
    @Transactional
    public MessageResponse createDiscountAndAssociatedProducts(
            CreateMaGiamGiaCreateRequest createMaGiamGiaCreateRequest
    ) {
        GiamGia giamGia = GiamGiaMapper.giamGiaToDto(createMaGiamGiaCreateRequest);
        GiamGia savedGiamGia = giamGiaRepository.save(giamGia);

        if (savedGiamGia == null || savedGiamGia.getId() == null) {
            return MessageResponse.builder().message("Không thể lưu mã giảm giá").build();
        }
        Optional<SanPhamChiTiet> productDetailOptional = productDetailRepository.findById(createMaGiamGiaCreateRequest.getIdSanPham());
        if (productDetailOptional.isEmpty()) {
            return MessageResponse.builder().message("Sản phẩm không tồn tại").build();
        }

        for (SpGiamGia spGiamGia : savedGiamGia.getSpGiamGiaList()) {
            spGiamGia.setId(UUID.randomUUID());
            spGiamGia.setSanPhamChiTiet(productDetailOptional.get());
            spGiamGia.setGiamGia(savedGiamGia);
            spGiamGia.setTrangThai(1);
            spGiamGiaRepository.save(spGiamGia);
        }

        return new MessageResponse("Thêm thành công");
    }

}
