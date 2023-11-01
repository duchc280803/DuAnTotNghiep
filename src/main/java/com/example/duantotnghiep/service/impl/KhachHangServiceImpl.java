package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.KhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KhachHangServiceImpl implements KhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public List<KhachHangResponse> getKhachHang() {
        return khachHangRepository.findlistKhachHang();
    }

    @Override
    public List<KhachHangResponse> findByKeyToKhachHang(String key) {
        return khachHangRepository.findByKeyToKhachHang(key);
    }

    @Override
    public MessageResponse createKhachHang(CreateKhachRequest createKhachRequest) {
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(createKhachRequest.getHoTen());
        taiKhoan.setSoDienThoai(createKhachRequest.getSoDienThoai());
        taiKhoan.setEmail(createKhachRequest.getEmail());
        taiKhoan.setNgaySinh(createKhachRequest.getNgaySinh());
        khachHangRepository.save(taiKhoan);
        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(createKhachRequest.getDiaChi());
        diaChi.setTaiKhoan(taiKhoan);
        diaChiRepository.save(diaChi);
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    @Override
    public MessageResponse updateHoaDon(UUID id, UUID idHoaDon) {

        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        if (hoaDon.isEmpty()) {
            return MessageResponse.builder().message("Hóa Đơn Null").build();
        }
        Optional<TaiKhoan> khachHang = khachHangRepository.findById(id);
        if (khachHang.isEmpty()) {
            return MessageResponse.builder().message("Khách Hàng Null").build();
        }
        hoaDon.get().setTaiKhoanKhachHang(khachHang.get());
        hoaDonRepository.save(hoaDon.get());

        return MessageResponse.builder().message("Update Thành Công").build();
    }

    @Override
    public KhachHangResponse detailKhachHang(UUID id) {
        return khachHangRepository.detailKhachHang(id);
    }

    @Override
    public MessageResponse updateKhachVaoGioHang(UUID id, UUID idGioHang) {
        Optional<GioHang> findByGioHang = gioHangRepository.findById(idGioHang);
        if (findByGioHang.isEmpty()) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }
        Optional<TaiKhoan> khachHang = khachHangRepository.findById(id);
        if (khachHang.isEmpty()) {
            return MessageResponse.builder().message("Khách Hàng Null").build();
        }
        findByGioHang.get().setTaiKhoan(khachHang.get());

        return null;
    }

}
