package com.example.duantotnghiep.controller.ban_tai_quay_controller;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.ProductCounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/chi-tiet-sp")
public class ProductCounterController {

    @Autowired
    private ProductCounterServiceImpl chiTietSanPhamService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<ChiTietSanPhamCustom>> getAll() {
        return ResponseEntity.ok(chiTietSanPhamService.getAll());
    }

    @GetMapping()
    public ResponseEntity<List<ChiTietSanPhamCustom>> searchByName(
            @RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.searchByName(name));
    }

    @GetMapping("san-pham/{id}")
    public ResponseEntity<SanPhamGetAllResponse> searchByIdSp(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(chiTietSanPhamService.getByIdSp(id), HttpStatus.OK);
    }

    @GetMapping("san-pham-detail-soluong")
    public ResponseEntity<DetailQuantityToSizeReponse> getDetailQuantityToSizeReponse(
            @RequestParam("id") UUID id,
            @RequestParam("size") Integer size) {
        return new ResponseEntity<>(chiTietSanPhamService.getDetailQuantityToSizeReponse(id, size), HttpStatus.OK);
    }

}
