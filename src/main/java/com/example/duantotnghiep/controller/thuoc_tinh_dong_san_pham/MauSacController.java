package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.entity.Size;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.request.SizeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.MauSacServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/mau-sac/")
public class MauSacController {

    @Autowired
    private MauSacServiceImpl mauSacService;

    @GetMapping("show")
    public ResponseEntity<List<MauSac>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(mauSacService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<MauSac>> getAllMauSac(
            @RequestParam(name = "tenMauSac", required = false) String tenMauSac,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(mauSacService.getAllMauSac(trangThai, tenMauSac, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("hien-thi/{id}")
    public MauSac getSizeById(@PathVariable UUID id) {
        return mauSacService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createSize(@RequestBody MauSacRequest mauSacRequest) {
        return new ResponseEntity<>(mauSacService.create(mauSacRequest), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateSize(@PathVariable UUID id, @RequestBody MauSacRequest mauSacRequest) {
        try {
            MessageResponse response = mauSacService.update(id, mauSacRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteSize(@PathVariable UUID id) {
        return new ResponseEntity<>(mauSacService.delete(id), HttpStatus.OK);
    }
}
