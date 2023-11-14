package com.example.duantotnghiep.service.voucher_service.impl;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.repository.VoucherRepository;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.voucher_service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceimpl implements VoucherService {
    @Autowired
    private VoucherRepository Repository;

    @Override
    public List<Voucher> getAll() {
        return Repository.findAll();
    }

    @Override
    public MessageResponse createVoucher(VoucherRequest createVoucherRequest) {
        Voucher voucher = new Voucher();
        voucher.setId(UUID.randomUUID());
        voucher.setMaVoucher(createVoucherRequest.getMaVoucher());
        voucher.setTenVoucher(createVoucherRequest.getTenVoucher());
        voucher.setGiaTriToiThieuDonhang(createVoucherRequest.getGiaTriToiThieuDonhang());
        voucher.setGiaTriGiam(createVoucherRequest.getGiaTriGiam());
        voucher.setSoLuongMa(createVoucherRequest.getSoLuongMa());
        voucher.setNgayBatDau(createVoucherRequest.getNgayBatDau());
        voucher.setNgayKetThuc(createVoucherRequest.getNgayKetThuc());
        voucher.setHinhThucGiam(createVoucherRequest.getHinhThucGiam());
        voucher.setTrangThai(createVoucherRequest.getTrangThai());
        Repository.save(voucher);
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    @Override
    public MessageResponse updateVoucher(UUID id, VoucherRequest createVoucherRequest) {
        Voucher voucher = Repository.findById(id).orElse(null);
        if (voucher != null) {
            voucher.setMaVoucher(createVoucherRequest.getMaVoucher());
            voucher.setTenVoucher(createVoucherRequest.getTenVoucher());
            voucher.setGiaTriToiThieuDonhang(createVoucherRequest.getGiaTriToiThieuDonhang());
            voucher.setGiaTriGiam(createVoucherRequest.getGiaTriGiam());
            voucher.setSoLuongMa(createVoucherRequest.getSoLuongMa());
            voucher.setNgayBatDau(createVoucherRequest.getNgayBatDau());
            voucher.setNgayKetThuc(createVoucherRequest.getNgayKetThuc());
            voucher.setHinhThucGiam(createVoucherRequest.getHinhThucGiam());
            voucher.setTrangThai(createVoucherRequest.getTrangThai());
            Repository.save(voucher);

            return MessageResponse.builder().message("Cập nhật Thành Công").build();
        } else {
            // Handle the case where the discount is not found
            return MessageResponse.builder().message("Không tìm thấy giảm giá").build();
        }
    }

    @Override
    public Voucher findById(UUID id) {
        return Repository.findById(id).orElseThrow(() -> new RuntimeException("Voucher not found"));
    }

    @Override
    public List<Voucher> searchByTenOrMaVoucher(String keyword) {
        return Repository.findByTenVoucherContainingIgnoreCaseOrMaVoucherContainingIgnoreCase(keyword, keyword);
    }
}
