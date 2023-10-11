package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.ChiTietSanPhamService;
import com.example.duantotnghiep.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private NhanVienRepository nhanVienRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Override
    @Transactional
    //TODO Thêm hóa đơn tại quầy
    public MessageResponse taoHoaDon(String name) {

        Optional<NhanVien> findByNhanVien = nhanVienRepository.findByUsername(name);
        if (findByNhanVien.isEmpty()) {
            return MessageResponse.builder().message("Nhân viên không tồn tại").build();
        }

        Random rand = new Random();
        int randomNumber = rand.nextInt(1000); // Số ngẫu nhiên từ 0 đến 999
        String maHd = String.format("HD%03d", randomNumber); // Định dạng thành "HD001", "HD002", ...

        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
//        hoaDon.setNgayTao(LocalDate.now());
        hoaDon.setTrangThai(1);
        hoaDon.setNhanVien(findByNhanVien.get());
        hoaDonRepository.save(hoaDon);
        return MessageResponse.builder().message("Tạo hóa đơn thành công").build();
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay() {
        return hoaDonRepository.viewHoaDonTaiQuay();
    }

    @Override
    public MessageResponse updateHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoaDonThanhToanRequest.getIdHoaDon());
        hoaDon.get().setNgayNhan(hoaDonThanhToanRequest.getNgayNhan());
        hoaDon.get().setTienKhachTra(hoaDonThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonThanhToanRequest.getTongTien());
        hoaDonRepository.save(hoaDon.get());

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToan.setKhachHang(hoaDon.get().getKhachHang());
        hinhThucThanhToan.setPhuongThucThanhToan(1);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);

        for (UUID idGioHangChiTiet : hoaDonThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setId(UUID.randomUUID());
                hoaDonChiTiet.setHoaDon(hoaDon.get());
                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());
                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getGiaBan());
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(1);
                hoaDonChiTietRepository.save(hoaDonChiTiet);
            }
        }
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }
}
