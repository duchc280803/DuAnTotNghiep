package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.service.impl.ChatLieuServiceImpl;
import com.example.duantotnghiep.service.impl.GiamGiaServiceimpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/giam-gia/")
public class GiamGiaController {
    @Autowired
    private GiamGiaServiceimpl Service;

    @GetMapping("show")
    public ResponseEntity<List<GiamGiaResponse>> getAllGiamGia() {
        return new ResponseEntity<>(Service.getAll(), HttpStatus.OK);
    }
    // search by 
    @GetMapping("searchString_bykey")
    public ResponseEntity<GiamGiaResponse>findByKhachHangByIdHoaDon(@RequestParam(name = "key") String key) {
        return new ResponseEntity<>(Service.findbyValueString(key), HttpStatus.OK);
    }


    @GetMapping("searchDatebykey")
    public ResponseEntity<List<GiamGiaResponse>> findByKhachHangB(
            @RequestParam(name = "key1") @DateTimeFormat(pattern = "yyyy-MM-dd") Date key1,
            @RequestParam(name = "key2") @DateTimeFormat(pattern = "yyyy-MM-dd") Date key2) {

        List<GiamGiaResponse> result = Service.findbyValueDate(key1, key2);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping("searchStatus_bykey")
    public ResponseEntity<List<GiamGiaResponse>>findByKha(@RequestParam(name = "key") Integer key) {
        return new ResponseEntity<>(Service.findbyValueStatus(key), HttpStatus.OK);
    }
}
