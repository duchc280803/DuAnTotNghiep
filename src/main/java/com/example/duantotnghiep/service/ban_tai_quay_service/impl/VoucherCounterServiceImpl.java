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
        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<Voucher> optionalVoucher = voucherRepository.findById(idVoucher);

        if (optionalHoaDon.isPresent() && optionalVoucher.isPresent()) {
            HoaDon hoaDon = optionalHoaDon.get();
            Voucher voucher = optionalVoucher.get();

            hoaDon.setVoucher(voucher);

            if (voucher.getHinhThucGiam() == 1) {
                BigDecimal phanTramGiam = new BigDecimal(voucher.getGiaTriGiam()).divide(new BigDecimal(100));
                BigDecimal tienGiam = thanhTien.multiply(phanTramGiam);
                hoaDon.setTienGiamGia(tienGiam);
            } else if (voucher.getHinhThucGiam() == 2) {
                hoaDon.setTienGiamGia(new BigDecimal(voucher.getGiaTriGiam()));
            }

            hoaDonRepository.save(hoaDon);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        }

        return MessageResponse.builder().message("Không tìm thấy HoaDon hoặc Voucher").build();
    }

    @Override
    public String findByName(UUID id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        if (hoaDon == null || hoaDon.getVoucher() == null) {
            return "Voucher null";
        }
        String name = hoaDon.getVoucher().getMaVoucher();
        if (name.isEmpty()) {
            return "Voucher null";
        }
        return name;
    }

}
