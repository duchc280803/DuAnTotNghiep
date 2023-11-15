package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.VoucherRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.VoucherCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherCounterServiceImpl implements VoucherCounterService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public List<VoucherCounterResponse> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<VoucherCounterResponse> voucherCounterResponses = voucherRepository.findAllVoucher(pageable);
        return voucherCounterResponses.getContent();
    }

    @Override
    public MessageResponse addVoucherOrder(UUID idHoaDon, UUID idVoucher, BigDecimal thanhTien) {
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<Voucher> voucher = voucherRepository.findById(idVoucher);
        if (hoaDon.isPresent()) {
            hoaDon.get().setVoucher(voucher.get());
            if (voucher.get().getHinhThucGiam() == 1) {
                Long phanTramGiam = voucher.get().getGiaTriGiam() / 100;
                hoaDon.get().setTienGiamGia(thanhTien.multiply(new BigDecimal(phanTramGiam)));
            }
            if (voucher.get().getHinhThucGiam() == 2) {
                hoaDon.get().setTienGiamGia(thanhTien.subtract(new BigDecimal(voucher.get().getGiaTriGiam())));
            }
        }
        hoaDonRepository.save(hoaDon.get());
        return MessageResponse.builder().message("Update thành công").build();
    }
}
