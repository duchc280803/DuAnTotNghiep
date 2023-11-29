package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.not_login.*;
import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import com.example.duantotnghiep.response.GiamGiaResponse;

import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.impl.ProductCounterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/san-pham-giam-gia/")
public class SanPhamGiamGiaController {

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private ProductCounterServiceImpl productCounterService;

    @GetMapping("show")
    public ResponseEntity<List<ChiTietSanPhamCustom>> show(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return ResponseEntity.ok(productCounterService.getAll(pageNumber, pageSize));
    }

    @GetMapping("detailList")
    public ResponseEntity<List<loadsanpham_not_login>> ListDetail(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllSpGiamGiabyDanhMuc(id), HttpStatus.OK);
    }

    @GetMapping("searchString_bykey")
    public ResponseEntity<List<loadsanpham_not_login>> findByKhachHangByIdHoaDon(@RequestParam(name = "key") String
                                                                                         key) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllSpGiamGiabyKey(key), HttpStatus.OK);
    }

    @GetMapping("show-sp-lien-quan")
    public ResponseEntity<List<ChiTietSanPhamCustom>> showSpLienQuan(
            @RequestParam UUID idthuonghieu,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "8") Integer pageSize
    ) {
        return ResponseEntity.ok(productCounterService.getSanPhamLienQuan(idthuonghieu, pageNumber, pageSize));
    }

    @GetMapping("show-name-price-image/{name}")
    public ResponseEntity<ChiTietSanPhamCustom> getNamePriceImage(@PathVariable String name) {
        return ResponseEntity.ok(productCounterService.getOne(name));
    }

    @GetMapping("show-all-mau-sac/{name}")
    public ResponseEntity<List<loadmausac_not_login>> getAllMauSac(@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllMauSac(name), HttpStatus.OK);
    }

    @GetMapping("show-all-size/{name}")
    public ResponseEntity<List<loadsize_not_login>> getAllSize(@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllSize(name), HttpStatus.OK);
    }

    @GetMapping("show-all-chat-lieu/{name}")
    public ResponseEntity<List<loadchatlieu_not_login>> getAllChatLieu(@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllChatLieu(name), HttpStatus.OK);
    }

    @GetMapping("find-by-mau-sac/{name}")
    public ResponseEntity<List<loadsize_chatlieu_not_login>> findByMauSac(@PathVariable(name = "name") String name,
                                                                          @RequestParam UUID idmausac) {
        return new ResponseEntity<>(spGiamGiaRepository.findSizeChatLieu(name, idmausac), HttpStatus.OK);
    }

    @GetMapping("find-by-size/{name}")
    public ResponseEntity<List<loadmausac_chatlieu_not_login>> findMauSacChatLieuBySize(@PathVariable String name,
                                                                                        @RequestParam UUID idsize) {
        return new ResponseEntity<>(spGiamGiaRepository.findMauSacChatLieu(name, idsize), HttpStatus.OK);
    }

    @GetMapping("find-by-chat-lieu/{name}")
    public ResponseEntity<List<loadmausac_size_not_login>> findSizeMauSacByChatLieu(@PathVariable String name,
                                                                                    @RequestParam UUID idchatlieu) {
        return new ResponseEntity<>(spGiamGiaRepository.findSizeMauSac(name, idchatlieu), HttpStatus.OK);
    }

    @GetMapping("find-idspct-soluong/{name}")
    public ResponseEntity<findIdSpctAndSoLuong_not_login> findIdSpctAndSoLuong_not_login(
            @RequestParam UUID idmausac,
            @RequestParam UUID idsize,
            @RequestParam UUID idchatlieu,
            @PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.findIdspctAndSoluong(idmausac, idsize, idchatlieu, name),
                HttpStatus.OK);
    }

    @GetMapping("searchMoneybykey")
    public ResponseEntity<List<loadsanpham_not_login>> findByKhachHangB(
            @RequestParam(name = "key1") BigDecimal key1,
            @RequestParam(name = "key2") BigDecimal key2) {

        List<loadsanpham_not_login> result = spGiamGiaRepository.getAllSpGiamGiabyTien(key1, key2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}