package com.example.duantotnghiep.service.voucher_service.impl;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.repository.VoucherRepository;
import com.example.duantotnghiep.request.VoucherRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.voucher_service.VoucherService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceimpl implements VoucherService {

    @Autowired
    private VoucherRepository Repository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;


    @Override
    public List<Voucher> listVoucher(String maGiamGia, String tenGiamGia, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Voucher> result = Repository.listVoucher(maGiamGia, tenGiamGia, trangThai, pageable);
        return result.getContent();
}


    @Override
    @Transactional
    public MessageResponse updateVoucherstaus(UUID id) {
        Voucher existingGiamGia = Repository.findById(id).orElse(null);

        if (existingGiamGia != null) {
            if (existingGiamGia.getTrangThai() == 2) {
                // TrangThai = 2: Đang kích hoạt, cập nhật thành TrangThai = 1 và setNgayKetthuc = ngày hôm nay
                existingGiamGia.setTrangThai(1);
                Date currentDate = new Date();
                if (existingGiamGia.getNgayKetThuc() != null && existingGiamGia.getNgayKetThuc().before(currentDate)) {
                    existingGiamGia.setNgayKetThuc(currentDate);
                }
                return MessageResponse.builder().message("Cập nhật Thành Công").build();
            } else {
                existingGiamGia.setTrangThai(2);
                // TrangThai không phải là 2, có thể xử lý thêm theo nhu cầu của bạn
                return MessageResponse.builder().message("Trạng thái không hợp lệ hoặc đã được cập nhật trước đó").build();
            }
        } else {
            // Handle the case where the discount is not found
            return MessageResponse.builder().message("Không tìm thấy voucher").build();
        }
    }

    @Override
    public MessageResponse createVoucher(VoucherRequest createVoucherRequest, String username)
            throws IOException, CsvValidationException {
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        Voucher voucher = new Voucher();
        voucher.setId(UUID.randomUUID());
        voucher.setMaVoucher(createVoucherRequest.getMaVoucher());
        voucher.setTenVoucher(createVoucherRequest.getTenVoucher());
        voucher.setGiaTriToiThieuDonhang(createVoucherRequest.getGiaTriToiThieuDonhang());
        voucher.setGiaTriGiam(createVoucherRequest.getGiaTriGiam());
        voucher.setSoLuongMa(createVoucherRequest.getSoLuongMa());
        voucher.setSoLuongDung(0);
        voucher.setNgayBatDau(createVoucherRequest.getNgayBatDau());
        voucher.setNgayKetThuc(createVoucherRequest.getNgayKetThuc());
        voucher.setHinhThucGiam(createVoucherRequest.getHinhThucGiam());
        Date currentDate = new Date();
        voucher.setNgayTao(currentDate);
        if (createVoucherRequest.getNgayBatDau().after(currentDate)) {
            voucher.setTrangThai(3);
        } else {
            voucher.setTrangThai(1);
        }
        Repository.save(voucher);
        auditLogService.writeAuditLogVoucher("Tạo Voucher", username, taiKhoanUser.getEmail(), null,
                "Mã : " + createVoucherRequest.getMaVoucher() + "," + "Tên :" + createVoucherRequest.getTenVoucher()
                        + "," + "Giá trị tối thiểu : " + createVoucherRequest.getGiaTriToiThieuDonhang() + ","
                        + "Giá trị giảm : " + createVoucherRequest.getGiaTriGiam() + "," + "Số lượng Giảm : "
                        + createVoucherRequest.getSoLuongMa()+  "," +"Ngày Bắt đầu : "
                        + createVoucherRequest.getNgayBatDau() + "," + "Ngày Kết thúc :"
                        + createVoucherRequest.getNgayKetThuc(),
                null, null, null);
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    @Override
    public MessageResponse updateVoucher(UUID id, VoucherRequest createVoucherRequest, String username)
            throws IOException, CsvValidationException {
        Voucher voucher = Repository.findById(id).orElse(null);
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
        if (voucher != null) {
            voucher.setMaVoucher(createVoucherRequest.getMaVoucher());
            voucher.setTenVoucher(createVoucherRequest.getTenVoucher());
            voucher.setGiaTriToiThieuDonhang(createVoucherRequest.getGiaTriToiThieuDonhang());
            voucher.setGiaTriGiam(createVoucherRequest.getGiaTriGiam());
            voucher.setSoLuongMa(createVoucherRequest.getSoLuongMa());
            voucher.setNgayBatDau(createVoucherRequest.getNgayBatDau());
            voucher.setNgayKetThuc(createVoucherRequest.getNgayKetThuc());
            voucher.setHinhThucGiam(createVoucherRequest.getHinhThucGiam());
            Date currentDate = new Date();
            voucher.setNgayCapNhap(currentDate);
            if (createVoucherRequest.getNgayBatDau().after(currentDate)) {
                voucher.setTrangThai(3);
            } else {
                voucher.setTrangThai(1);
            }
            Repository.save(voucher);
            auditLogService.writeAuditLogVoucher("Cập Nhật Voucher", username, taiKhoanUser.getEmail(), null,
                    "Mã : " + createVoucherRequest.getMaVoucher() + "," + "Tên :" + createVoucherRequest.getTenVoucher()
                            + "," + "Giá trị tối thiểu : " + createVoucherRequest.getGiaTriToiThieuDonhang() + ","
                            + "Giá trị giảm : " + createVoucherRequest.getGiaTriGiam() + "," + "Số lượng Giảm : "
                            + createVoucherRequest.getSoLuongMa() +  "," + "Ngày Bắt đầu : "
                            + createVoucherRequest.getNgayBatDau() + "," + "Ngày Kết thúc :"
                            + createVoucherRequest.getNgayKetThuc(),
                    null, null, null);
            return MessageResponse.builder().message("Cập nhật Thành Công").build();
        } else {
            // Handle the case where the discount is not found
            return MessageResponse.builder().message("Không tìm thấy giảm giá").build();
        }
    }

    @Override
    public MessageResponse checkAndSetStatus() {
        List<Voucher> voucherList = Repository.findAll();
        Date currentDate = new Date(); // Ngày hiện tại

        for (Voucher voucher : voucherList) {
            if (voucher.getNgayKetThuc().before(currentDate)) {
                // Nếu ngày kết thúc đã qua so với ngày hiện tại
                if (voucher.getTrangThai() != null && voucher.getTrangThai() == 1) {
                    // Nếu trạng thái là 1 (đang hoạt động), thì cập nhật trạng thái thành 2 (đã kết
                    // thúc)
                    voucher.setTrangThai(2);
                    Repository.save(voucher);
                }
            }
        }
        return MessageResponse.builder().message("Ok").build();
    }


    @Override
    public Voucher findById(UUID id) {
        return Repository.findById(id).orElseThrow(() -> new RuntimeException("Voucher not found"));
    }

    @Override
    public List<Voucher> searchByTrangThai(Integer trangThai) {
        return Repository.findByTrangThai(trangThai);
    }

    @Override
    public List<Voucher> searchByTenOrMaVoucher(String keyword) {
        return Repository.findByTenVoucherContainingIgnoreCaseOrMaVoucherContainingIgnoreCase(keyword, keyword);
    }
}
