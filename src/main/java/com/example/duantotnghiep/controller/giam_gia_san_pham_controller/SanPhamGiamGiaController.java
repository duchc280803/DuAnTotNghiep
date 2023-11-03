package com.example.duantotnghiep.controller.giam_gia_san_pham_controller;

import com.example.duantotnghiep.mapper.not_login.findIdSpctAndSoLuong_not_login;
import com.example.duantotnghiep.mapper.not_login.loadmausac_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsanpham_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsize_not_login;
import com.example.duantotnghiep.repository.SpGiamGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/san-pham-giam-gia/")
public class SanPhamGiamGiaController {

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @GetMapping("show-name-price-image")
    public ResponseEntity<List<loadsanpham_not_login>> getNamePriceImage(@RequestParam UUID idsanpham) {

        return ResponseEntity.ok(spGiamGiaRepository.getNamePriceImageByIdSanPham(idsanpham));
    }
    @GetMapping("show-all-mau-sac")
    public ResponseEntity<List<loadmausac_not_login>> getAllMauSac(@RequestParam UUID idsanpham) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllMauSac(idsanpham), HttpStatus.OK);
    }

    @GetMapping("show-all-size")
    public ResponseEntity<List<loadsize_not_login>> getAllSize(@RequestParam UUID idsanpham) {
        return new ResponseEntity<>(spGiamGiaRepository.getAllSize(idsanpham), HttpStatus.OK);
    }

    @GetMapping("find-size-by-mau-sac")
    public ResponseEntity<List<loadsize_not_login>> findSizeByMauSac(@RequestParam UUID idsanpham,@RequestParam UUID idmausac) {
        return new ResponseEntity<>(spGiamGiaRepository.findSizeByMauSac(idsanpham,idmausac), HttpStatus.OK);
    }

    @GetMapping("find-mau-sac-by-size")
    public ResponseEntity<List<loadmausac_not_login>> findMauSacBySize(@RequestParam UUID idsanpham,@RequestParam UUID idsize) {
        return new ResponseEntity<>(spGiamGiaRepository.findMauSacBySize(idsanpham,idsize), HttpStatus.OK);
    }

    @GetMapping("find-idspct-soluong")
    public ResponseEntity<List<findIdSpctAndSoLuong_not_login>> findIdSpctAndSoLuong_not_login(@RequestParam UUID idmausac, @RequestParam UUID idsize,@RequestParam UUID idsanpham) {
        return new ResponseEntity<>(spGiamGiaRepository.findIdspctAndSoluong(idmausac,idsize,idsanpham), HttpStatus.OK);
    }
}
