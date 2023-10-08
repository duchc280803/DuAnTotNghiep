package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.service.KhachHangService;
import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/khach-hang/")
public class KhachHangController {
    @Autowired
    private KhachHangImpl khachHangService;

    @GetMapping("hien-thi")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(khachHangService.getKhachHang());
    }

    @GetMapping("create")
    public ResponseEntity<?> create(@RequestBody TaiKhoan taiKhoan) {
        String error = "";
//        if (ObjectUtils.isEmpty(taiKhoan.getMaphieu())) {
//            error = "không được trống";
//        } else if (ObjectUtils.isEmpty(taiKhoan.getTenphieu())) {
//            error = "không được trống";
//        }else if (ObjectUtils.isEmpty(taiKhoan.getTenphieu())) {
//            error = "không được trống";
//        }else if (ObjectUtils.isEmpty(taiKhoan.getGiatrigiam())) {
//            error = "không được trống";
//        }else if (ObjectUtils.isEmpty(taiKhoan.getHinhthucgiam())) {
//            error = "không được trống";
//        }else if (ObjectUtils.isEmpty(taiKhoan.getKhachHang().getMakhachhang())) {
//            error = "không được trống";
//        }
//        if (!error.isEmpty()) {
//            return ResponseEntity.badRequest().body(error);
//        }
        khachHangService.save(taiKhoan);
        return ResponseEntity.ok(khachHangService.save(taiKhoan));
    }


}
