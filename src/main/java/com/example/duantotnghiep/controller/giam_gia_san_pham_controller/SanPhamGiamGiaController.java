package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.mapper.not_login.findIdSpctAndSoLuong_not_login;
import com.example.duantotnghiep.mapper.not_login.loadmausac_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsanpham_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsize_not_login;
import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/san-pham-giam-gia/")
public class SanPhamGiamGiaController {

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @GetMapping("show")
    public ResponseEntity<List<loadsanpham_not_login>> show() {
        return ResponseEntity.ok(spGiamGiaRepository.getAllSpGiamGia());
    }

    @GetMapping("show-name-price-image/{name}")
    public ResponseEntity<List<loadsanpham_not_login>> getNamePriceImage(@PathVariable String name) {
        return ResponseEntity.ok(spGiamGiaRepository.getNamePriceImageByIdSanPham(name));
    }
    @GetMapping("show-all-mau-sac/{name}")
    public ResponseEntity<List<loadmausac_not_login>> getAllMauSac(@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllMauSac(name), HttpStatus.OK);
    }

    @GetMapping("show-all-size/{name}")
    public ResponseEntity<List<loadsize_not_login>> getAllSize(@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllSize(name), HttpStatus.OK);
    }

    @GetMapping("find-size-by-mau-sac/{name}")
    public ResponseEntity<List<loadsize_not_login>> findSizeByMauSac(@PathVariable String name,@RequestParam UUID idmausac) {
        return new ResponseEntity<>(spGiamGiaRepository.findSizeByMauSac(name,idmausac), HttpStatus.OK);
    }

    @GetMapping("find-mau-sac-by-size/{name}")
    public ResponseEntity<List<loadmausac_not_login>> findMauSacBySize(@PathVariable String name,@RequestParam UUID idsize) {
        return new ResponseEntity<>(spGiamGiaRepository.findMauSacBySize(name,idsize), HttpStatus.OK);
    }

    @GetMapping("find-idspct-soluong/{name}")
    public ResponseEntity<List<findIdSpctAndSoLuong_not_login>> findIdSpctAndSoLuong_not_login(@RequestParam UUID idmausac, @RequestParam UUID idsize,@PathVariable String name) {
        return new ResponseEntity<>(spGiamGiaRepository.findIdspctAndSoluong(idmausac,idsize,name), HttpStatus.OK);
    }
}
