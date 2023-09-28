package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.service.impl.ChiTietSanPhamServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    } //close getAll()

    @GetMapping()
    public ResponseEntity<?> searchByName(@RequestParam String name){
        return ResponseEntity.ok(chiTietSanPhamService.searchByName(name));
    }//close searchName

    @GetMapping("san-pham/{id}")
    public ResponseEntity<?> searchByIdSp(@PathVariable UUID id){
        return ResponseEntity.ok(chiTietSanPhamService.getByIdSp(id));
    }//close searchID

    //filter theo thuoc tinh

    @GetMapping("san-pham/danh-muc/{idDanhMuc}")
    public ResponseEntity<?> findByDanhMuc(@PathVariable UUID idDanhMuc){
        return ResponseEntity.ok(chiTietSanPhamService.findByDanhMuc(idDanhMuc));
    }//close findByDanhMuc

    @GetMapping("san-pham/xuat-su/{idXuatSu}")
    public ResponseEntity<?> findByXuatSu(@PathVariable UUID idXuatSu){
        return ResponseEntity.ok(chiTietSanPhamService.findByXuatSu(idXuatSu));
    }//close findByDanhMuc

    @GetMapping("san-pham/thuong-hieu/{idKieuDe}")
    public ResponseEntity<?> findByThuongHieu(@PathVariable UUID idThuongHieu){
        return ResponseEntity.ok(chiTietSanPhamService.findByThuongHieu(idThuongHieu));
    }//close findByThuongHieu

    @GetMapping("san-pham/kieu-de/{idKieuDe}")
    public ResponseEntity<?> findByKieuDe(@PathVariable UUID idKieuDe){
        return ResponseEntity.ok(chiTietSanPhamService.findByKieuDe(idKieuDe));
    }//close findByKieuDe

    @GetMapping("san-pham/chat-lieu/{idChatLieu}")
    public ResponseEntity<?> findByChatLieu(@PathVariable UUID idChatLieu){
        return ResponseEntity.ok(chiTietSanPhamService.findByChatLieu(idChatLieu));
    }//close findByChatLieu

    @GetMapping("san-pham/mau-sac/{idMauSac}")
    public ResponseEntity<?> findByMauSac(@PathVariable UUID idMauSac){
        return ResponseEntity.ok(chiTietSanPhamService.findByMauSac(idMauSac));
    }//close findByMauSac




    }//close class