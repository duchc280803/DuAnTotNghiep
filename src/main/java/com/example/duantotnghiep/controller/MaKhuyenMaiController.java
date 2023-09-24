package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.request.CreateMaGiamGiaCreateRequest;
import com.example.duantotnghiep.request.CreateSpGiamGiaRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.impl.MaKhuyenMaiServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/khuyen-mai/")
public class MaKhuyenMaiController {

    @Autowired
    private MaKhuyenMaiServiceImpl maKhuyenMaiService;

    @GetMapping("show-giam-gia")
    public ResponseEntity<List<GiamGiaRequest>> getAllGiamGia(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize
    ) {
        return ResponseEntity.ok(maKhuyenMaiService.listGiamGia(pageNumber, pageSize));
    }

    @GetMapping("show-product")
    public ResponseEntity<List<ProductDetailResponse>> getAllProduct() {
        return ResponseEntity.ok(maKhuyenMaiService.listProductDetailResponse());
    }

    @PostMapping("create-ma-giam-gia")
    public ResponseEntity<MessageResponse> createMaKhuyenMai(
            @Valid
            @RequestBody CreateMaGiamGiaCreateRequest createMaGiamGiaCreateRequest
    ) {
        return new ResponseEntity<>(
                maKhuyenMaiService.createDiscountAndAssociatedProducts(createMaGiamGiaCreateRequest),
                HttpStatus.CREATED
        );
    }
}
