package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.TaiKhoan;
<<<<<<< HEAD
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.service.KhachHangService;
import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
=======
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.service.KhachHangService;
import com.example.duantotnghiep.service.impl.KhachHangImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
>>>>>>> 8ad908c2030a56c77752838a166c22ab26456dbb
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.UUID;
=======
import java.util.List;
>>>>>>> 8ad908c2030a56c77752838a166c22ab26456dbb

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

    @GetMapping("detail")
    public ResponseEntity<KhachHangResponse> getDetailId(@RequestParam(name = "id") UUID id) {
        return new ResponseEntity<>(khachHangService.findByKhachHang(id), HttpStatus.OK);
    }

    @GetMapping("search")
    public ResponseEntity<KhachHangResponse> search(@RequestParam(name = "key") String key) {
        return new ResponseEntity<>(khachHangService.findByKeyToKhachHang(key), HttpStatus.OK);
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
