package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.DanhMucServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/danh-muc/")
public class DanhMucController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @GetMapping("show")
    public ResponseEntity<List<DanhMuc>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(danhMucService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<DanhMuc>> getAllDanhMuc(
            @RequestParam(name = "tenDanhMuc", required = false) String tenDanhMuc,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(danhMucService.getAllDanhMuc(trangThai, tenDanhMuc, pageNumber, pageSize), HttpStatus.OK);
    }
    @GetMapping("hien-thi/{id}")
    public DanhMuc getDanhMucById(@PathVariable UUID id) {
        return danhMucService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createDanhMuc(@RequestBody DanhMucRequest danhMucRequest) {
        return new ResponseEntity<>(danhMucService.create(danhMucRequest), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateDanhMuc(@PathVariable UUID id, @RequestBody DanhMucRequest danhMucRequest) {
        try {
            MessageResponse response = danhMucService.update(id, danhMucRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteDanhMuc(@PathVariable UUID id) {
        return new ResponseEntity<>(danhMucService.delete(id), HttpStatus.OK);
    }
}
