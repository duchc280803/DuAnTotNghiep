package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import com.example.duantotnghiep.service.impl.ChiTietSanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/api/chi-tiet-sp")
public class ChiTietSanPhamRestController {

    @Autowired
    private ChiTietSanPhamServiceImpl chiTietSanPhamService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(chiTietSanPhamService.getAll());
    } // close getAll()

    @GetMapping()
    public ResponseEntity<?> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(chiTietSanPhamService.searchByName(name));
    }// close searchName

    @GetMapping("san-pham/{id}")
    public ResponseEntity<SanPhamGetAllResponse> searchByIdSp(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(chiTietSanPhamService.getByIdSp(id), HttpStatus.OK);
    }// close searchID

    @GetMapping("san-pham-detail/{id}")
    public ResponseEntity<?> searchDetailSanPham(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(chiTietSanPhamService.getDetailSizeToSanPham(id), HttpStatus.OK);
    }// close searchID

    @GetMapping("san-pham-detail-soluong")
    public ResponseEntity<DetailQuantityToSizeReponse> getDetailQuantityToSizeReponse(
            @RequestParam("id") UUID id,
            @RequestParam("size") Integer size) {
        return new ResponseEntity<>(chiTietSanPhamService.getDetailQuantityToSizeReponse(id, size), HttpStatus.OK);
    }

}
