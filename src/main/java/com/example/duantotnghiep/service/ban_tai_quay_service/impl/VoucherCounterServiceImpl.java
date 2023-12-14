package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.VoucherRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.LoadVoucherCounterResponse;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.VoucherCounterService;
import com.example.duantotnghiep.util.FormatNumber;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public List<VoucherCounterResponse> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<VoucherCounterResponse> voucherCounterResponses = voucherRepository.findAllVoucher(pageable);
        return voucherCounterResponses.getContent();
    }

    @Override
    public MessageResponse addVoucherOrder(UUID idHoaDon, UUID idVoucher, BigDecimal thanhTien, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        Optional<HoaDon> optionalHoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<Voucher> optionalVoucher = voucherRepository.findByIdAndTrangThai(idVoucher, 1);

        if (idVoucher == null) {
            optionalHoaDon.get().setVoucher(null);
            optionalHoaDon.get().setTienGiamGia(thanhTien);
            hoaDonRepository.save(optionalHoaDon.get());
        } else {
            HoaDon hoaDon = optionalHoaDon.get();
            Voucher voucher = optionalVoucher.get();

            Double giaTriGiamPhanTram = 0.0;
            Long maxDiscount1 = 0L;
            if (voucher.getHinhThucGiam() == 1) {
                Long giaTriGiam = voucher.getGiaTriGiam();
                giaTriGiamPhanTram = giaTriGiam / 100.0;
                maxDiscount1 = thanhTien.multiply(new BigDecimal(giaTriGiamPhanTram)).longValue();
                hoaDon.setTienGiamGia(new BigDecimal(maxDiscount1));
                hoaDon.setVoucher(voucher);
                auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), optionalHoaDon.get().getMa(), "Cập nhật voucher", optionalHoaDon.get().getMa(), "Mã voucher: " + voucher.getMaVoucher(), "Tiền giảm giá: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount1)) + "đ", "", "");
            } else if (voucher.getHinhThucGiam() == 2) {
                hoaDon.setTienGiamGia(new BigDecimal(voucher.getGiaTriGiam()));
                hoaDon.setVoucher(voucher);
                auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), optionalHoaDon.get().getMa(), "Cập nhận voucher", optionalHoaDon.get().getMa(), "Mã voucher: " + voucher.getMaVoucher(), "Tiền giảm giá: " + FormatNumber.formatBigDecimal(new BigDecimal(voucher.getGiaTriGiam())) + "đ", "", "");
            }

            hoaDonRepository.save(hoaDon);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        }

        return MessageResponse.builder().message("Không tìm thấy HoaDon hoặc Voucher").build();
    }

    @Override
    public MessageResponse closeVoucherOrder(UUID idHoaDon, BigDecimal thanhTien) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        hoaDon.setVoucher(null);
        hoaDon.setTienGiamGia(thanhTien);
        hoaDonRepository.save(hoaDon);
        return MessageResponse.builder().message("Thành công").build();
    }

    @Override
    public String findByName(UUID id) {
        HoaDon hoaDon = hoaDonRepository.findById(id).orElse(null);
        if (hoaDon == null || hoaDon.getVoucher() == null) {
            return "Không áp dụng";
        }
        String name = hoaDon.getVoucher().getMaVoucher();
        if (name.isEmpty()) {
            return "Không áp dụng";
        }
        return name;
    }

    @Override
    public LoadVoucherCounterResponse voucherResponse(UUID idHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        if (hoaDon.getVoucher() == null) {
            return LoadVoucherCounterResponse.builder().id(null).tienGiam(BigDecimal.ZERO).build();
        }else {
            return LoadVoucherCounterResponse.builder().id(hoaDon.getVoucher().getId()).tienGiam(hoaDon.getTienGiamGia()).build();
        }
    }

}
