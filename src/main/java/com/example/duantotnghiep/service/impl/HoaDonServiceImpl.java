package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonCustomResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AccountRepository accountRepository;

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

        Optional<TaiKhoan> findByNhanVien = accountRepository.findByUsername(name);

        Random rand = new Random();
        int randomNumber = rand.nextInt(1000); // Số ngẫu nhiên từ 0 đến 999
        String maHd = String.format("HD%03d", randomNumber); // Định dạng thành "HD001", "HD002", ...

        LocalDate localDate = LocalDate.now();
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
        hoaDon.setNgayTao(utilDate);
        hoaDon.setTrangThai(StatusOrderEnums.CHO_THANH_TOAN.getValue());
        hoaDon.setTaiKhoanNhanVien(findByNhanVien.get());
        hoaDonRepository.save(hoaDon);
        return MessageResponse.builder().message("Tạo hóa đơn thành công").build();
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay() {
        return hoaDonRepository.viewHoaDonTaiQuay();
    }

    @Override
    public MessageResponse updateHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new java.sql.Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToan.setPhuongThucThanhToan(1);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);

        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoaDonThanhToanRequest.getIdHoaDon());
        hoaDon.get().setNgayNhan(hoaDonThanhToanRequest.getNgayNhan());
        hoaDon.get().setTienKhachTra(hoaDonThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonThanhToanRequest.getTongTien());
        hoaDon.get().setHinhThucThanhToan(hinhThucThanhToan);
        hoaDonRepository.save(hoaDon.get());

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

    @Override
    public List<HoaDonCustomResponse> getAllHoaDonAdmin(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonCustomResponse> pageList = hoaDonRepository.getAllHoaDonAdmin(pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonCustomResponse> getAllHoaDonAdminFilter(Integer trangThaiHD, Integer phuongThucThanhToan, Integer loaiDon, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonCustomResponse> pageList = hoaDonRepository.getAllHoaDonAdminFilter(trangThaiHD, phuongThucThanhToan, loaiDon, pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonCustomResponse> getAllHoaDonEmployee(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonCustomResponse> pageList = hoaDonRepository.getAllHoaDonEmployee(pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonCustomResponse> getAllHoaDonOfEmployeeFilter(String username, Integer trangThaiHD, Integer phuongThucThanhToan, Integer loaiDon, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonCustomResponse> pageList = hoaDonRepository.getAllHoaDonOfEmployeeFilter(username, trangThaiHD, phuongThucThanhToan, loaiDon, pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonCustomResponse> getAllHoaDonOfEmployeeDefault(String username, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonCustomResponse> pageList = hoaDonRepository.getAllHoaDonOfEmployeeDefault(username, pageable);
        return pageList.getContent();
    }
}
