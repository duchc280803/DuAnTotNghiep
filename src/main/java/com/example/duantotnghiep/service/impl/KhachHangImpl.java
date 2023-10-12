package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.repository.DiaChiRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
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
public class KhachHangImpl implements KhachHangService {

//    @Autowired
//    private TaiKhoanRepository khachHangRepository;
//
//    @Autowired
//    private DiaChiRepository diaChiRepository;
//
//    @Autowired
//    private HoaDonRepository hoaDonRepository;
//
//    @Override
//    public List<KhachHangMap> getKhachHang() {
//        return khachHangRepository.findlistKhachHang();
//    }
//
//    @Override
//    public KhachHangResponse findByKhachHang(UUID id, UUID idHoaDon) {
//        return khachHangRepository.findByKhachHang(id, idHoaDon);
//    }
//
//    @Override
//    public KhachHangResponse findByKeyToKhachHang(String key) {
//        return khachHangRepository.findByKeyToKhachHang(key);
//    }
//
//    @Override
//    public MessageResponse createKhachHang(CreateKhachRequest createKhachRequest) {
//        KhachHang khachHang = new KhachHang();
//        khachHang.setId(UUID.randomUUID());
//        khachHang.setHoVaTen(createKhachRequest.getHoTen());
//        khachHang.setSoDienThoai(createKhachRequest.getSoDienThoai());
//        khachHang.setEmail(createKhachRequest.getEmail());
//        khachHang.setNgaySinh(createKhachRequest.getNgaySinh());
//        khachHangRepository.save(khachHang);
//        DiaChi diaChi = new DiaChi();
//        diaChi.setId(UUID.randomUUID());
//        diaChi.setDiaChi(createKhachRequest.getDiaChi());
//        diaChi.setKhachHang(khachHang);
//        diaChiRepository.save(diaChi);
//        return MessageResponse.builder().message("Thêm Thành Công").build();
//    }
//
//    @Override
//    public MessageResponse updateHoaDon(UUID id, UUID idHoaDon) {
//        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
//        if (hoaDon.isEmpty()) {
//            return MessageResponse.builder().message("Hóa Đơn Null").build();
//        }
//        Optional<KhachHang> khachHang = khachHangRepository.findById(id);
//        if (khachHang.isEmpty()) {
//            return MessageResponse.builder().message("Khách Hàng Null").build();
//        }
//        hoaDon.get().setKhachHang(khachHang.get());
//        hoaDonRepository.save(hoaDon.get());
//        return MessageResponse.builder().message("Update Thành Công").build();
//    }

}
