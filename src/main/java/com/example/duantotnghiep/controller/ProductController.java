package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product/")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("detail/{name}")
    public ResponseEntity<ProductDetailResponse> detailResponseResponseEntity(@PathVariable("name") String name) {
        return new ResponseEntity<>(productService.detailProduct(name), HttpStatus.OK);
    }

    @GetMapping("detail-size/{name}")
    public ResponseEntity<List<SizeProductDetailResponse>> sizeProductDetailResponseResponseEntity(@PathVariable("name") String name) {
        return new ResponseEntity<>(productService.detailSizeProduct(name), HttpStatus.OK);
    }

    @GetMapping("quantity")
    public ResponseEntity<QuantityDetailResponse> quantityDetailResponse(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(productService.quantityDetailResponse(id), HttpStatus.OK);
    }
}
