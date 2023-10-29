package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.TypeOrderEnums;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private LoaiDonRepository loaiDonRepository;

    @Override
    @Transactional
    //TODO Thêm hóa đơn tại quầy
    public HoaDon taoHoaDon(String name) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(name);

        Optional<LoaiDon> findByLoaiDon = loaiDonRepository.findByTrangThai(TypeOrderEnums.TAI_QUAY.getValue());

        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
        hoaDon.setNgayTao(timestamp);
        hoaDon.setTrangThai(StatusOrderEnums.CHO_THANH_TOAN.getValue());
        hoaDon.setTaiKhoanNhanVien(findByNhanVien.get());
        hoaDon.setLoaiDon(findByLoaiDon.get());
        return hoaDonRepository.save(hoaDon);
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize){
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonResponse> hoaDonResponses = hoaDonRepository.viewHoaDonTaiQuay(pageable);
        return hoaDonResponses.getContent();
    }

    @Override
    public List<HoaDonResponse> findByCodeOrder(String ma) {
        return hoaDonRepository.findByCodeOrder(ma);
    }

    @Override
    public MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        hoaDon.get().setNgayNhan(new java.sql.Date(System.currentTimeMillis()));
        hoaDon.get().setNgayThanhToan(new java.sql.Date(System.currentTimeMillis()));
        hoaDon.get().setTienKhachTra(hoaDonThanhToanRequest.getTienKhachTra());
        hoaDon.get().setThanhTien(hoaDonThanhToanRequest.getTongTien());
        hoaDon.get().setTrangThai(5);
        hoaDonRepository.save(hoaDon.get());

        for (UUID idGioHangChiTiet : hoaDonThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setId(UUID.randomUUID());
                hoaDonChiTiet.setHoaDon(hoaDon.get());
                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());
                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(1);
                hoaDonChiTietRepository.save(hoaDonChiTiet);
            }
        }
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }

    @Override
    public IdGioHangResponse showIdGioHangCt(String name) {
        return hoaDonRepository.showIdGioHangCt(name);
    }
}
