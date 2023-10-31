package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.config.VnPayConfig;
import com.example.duantotnghiep.entity.HinhThucThanhToan;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.LoaiHinhThucThanhToan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.HinhThucThanhToanRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;
import com.example.duantotnghiep.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, Integer phuongThuc,TransactionRequest transactionRequest) {
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien(transactionRequest.getSoTien());
        hinhThucThanhToan.setPhuongThucThanhToan(phuongThuc);
        hinhThucThanhToan.setCodeTransaction(VnPayConfig.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

    @Override
    public List<TransactionResponse> findAllTran(UUID id) {
        return hinhThucThanhToanRepository.findAllTran(id);
    }
}
