package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.XuatXuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/xuat-xu/")
public class XuatXuController {

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    @GetMapping("show")
    public ResponseEntity<List<XuatXu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(xuatXuService.getAll(), HttpStatus.OK);
    }
    @GetMapping("hien-thi")
    public ResponseEntity<List<XuatXu>> getAllXuatXu(
            @RequestParam(name = "tenXuatXu", required = false) String tenXuatXu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(xuatXuService.getAllXuatXu(trangThai, tenXuatXu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("hien-thi/{id}")
    public XuatXu getXuatXuById(@PathVariable UUID id) {
        return xuatXuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createXuatXu(@RequestBody XuatXuRequest xuatXuRequest) {
        return new ResponseEntity<>(xuatXuService.create(xuatXuRequest), HttpStatus.CREATED);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateXuatXu(@PathVariable UUID id, @RequestBody  XuatXuRequest xuatXuRequest) {
        try {
            MessageResponse response = xuatXuService.update(id, xuatXuRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete/{id}")
    public ResponseEntity<MessageResponse> deleteXuatXu(@PathVariable UUID id) {
        return new ResponseEntity<>(xuatXuService.delete(id), HttpStatus.OK);
    }
}
