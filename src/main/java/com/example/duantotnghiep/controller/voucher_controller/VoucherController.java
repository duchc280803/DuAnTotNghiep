package com.example.duantotnghiep.controller.voucher_controller;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.voucher_service.impl.VoucherServiceimpl;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher/")
public class VoucherController {

    @Autowired
    private VoucherServiceimpl Service;

    @GetMapping("show")
    public ResponseEntity<List<Voucher>> getAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize) {
        return new ResponseEntity<>(Service.getAll(pageNumber, pageSize).getContent(), HttpStatus.OK);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createVoucher(@RequestBody VoucherRequest createKhachRequest,
            Principal principal) throws CsvValidationException, IOException {
        return new ResponseEntity<>(Service.createVoucher(createKhachRequest, principal.getName()), HttpStatus.CREATED);
    }

    // Thêm endpoint tìm kiếm theo tên hoặc mã voucher
    @GetMapping("search")
    public ResponseEntity<List<Voucher>> search(@RequestParam String keyword) {
        return new ResponseEntity<>(Service.searchByTenOrMaVoucher(keyword), HttpStatus.OK);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<MessageResponse> updateVoucher(@PathVariable UUID id,
            @RequestBody VoucherRequest createKhachRequest, Principal principal)
            throws IOException, CsvValidationException {
        Service.updateVoucher(id, createKhachRequest, principal.getName());
        return new ResponseEntity<>(
                MessageResponse.builder().message("Cập nhật thông tin giảm giá thành công.").build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Voucher> getVoucherById(@PathVariable UUID id) {
        Voucher voucher = Service.findById(id);
        return ResponseEntity.ok(voucher);
    }

    @GetMapping("searchByTrangThai")
    public ResponseEntity<List<Voucher>> searchByTrangThai(@RequestParam Integer trangThai) {
        return new ResponseEntity<>(Service.searchByTrangThai(trangThai), HttpStatus.OK);
    }

}
