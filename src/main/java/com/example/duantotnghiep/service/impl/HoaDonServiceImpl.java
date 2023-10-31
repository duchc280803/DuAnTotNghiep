package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.TypeOrderEnums;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;
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
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private LoaiDonRepository loaiDonRepository;

    @Override
    @Transactional
    //TODO Thêm hóa đơn tại quầy
    public MessageResponse taoHoaDon(String name) {

        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(name);

        Optional<LoaiDon> findByLoaiDon = loaiDonRepository.findByTrangThai(TypeOrderEnums.TAI_QUAY.getValue());

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
        hoaDon.setLoaiDon(findByLoaiDon.get());
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
        hinhThucThanhToan.setNgayThanhToan(new java.sql.Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTrangThai(1);
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

    @Override
    public List<HoaDonDTOResponse> getAllHoaDonAdmin(Integer trangThaiHD, Integer loaiDon, String tenNhanVien, String ma, String soDienThoai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonDTOResponse> pageList = hoaDonRepository.getAllHoaDonAdmin(trangThaiHD, loaiDon, tenNhanVien, ma, soDienThoai, pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonDTOResponse> getAllHoaDonStaff(Integer trangThaiHD, Integer loaiDon, String ma, String soDienThoai, String username, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonDTOResponse> pageList = hoaDonRepository.getAllHoaDonStaff(trangThaiHD, loaiDon, ma, soDienThoai, username, pageable);
        return pageList.getContent();
    }

    @Override
    public List<HoaDonDTOResponse> getAllHoaDonCTTStaff(Integer loaiDon, String ma, String soDienThoai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonDTOResponse> pageList = hoaDonRepository.getAllHoaDonCTTStaff(loaiDon, ma, soDienThoai, pageable);
        return pageList.getContent();
    }


}
