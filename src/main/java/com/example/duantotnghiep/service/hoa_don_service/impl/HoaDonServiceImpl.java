package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusCartDetailEnums;
import com.example.duantotnghiep.enums.StatusCartEnums;
import com.example.duantotnghiep.enums.StatusOrderDetailEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TokenResponse;
import com.example.duantotnghiep.service.hoa_don_service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Override
    public MessageResponse updateHoaDonGiaoTaiQuay(UUID idHoaDon, HoaDonGiaoThanhToanRequest hoaDonGiaoThanhToanRequest) {
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        hoaDon.get().setNgayNhan(new java.sql.Date(System.currentTimeMillis()));
        hoaDon.get().setNgayThanhToan(new java.sql.Date(System.currentTimeMillis()));
        hoaDon.get().setTienKhachTra(hoaDonGiaoThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonGiaoThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonGiaoThanhToanRequest.getTongTien());
        hoaDon.get().setTenNguoiNhan(hoaDonGiaoThanhToanRequest.getHoTen());
        hoaDon.get().setSdtNguoiNhan(hoaDonGiaoThanhToanRequest.getSoDienThoai());
        hoaDon.get().setDiaChi(hoaDonGiaoThanhToanRequest.getDiaChi());
        hoaDon.get().setTienShip(hoaDonGiaoThanhToanRequest.getTienGiao());
        hoaDon.get().setTenNguoiShip(hoaDonGiaoThanhToanRequest.getTenNguoiShip());
        hoaDon.get().setSdtNguoiShip(hoaDonGiaoThanhToanRequest.getSoDienThoaiNguoiShip());
        hoaDon.get().setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
        hoaDonRepository.save(hoaDon.get());

        for (UUID idGioHangChiTiet : hoaDonGiaoThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            gioHangChiTiet.get().setTrangThai(StatusCartDetailEnums.DA_THANH_TOAN.getValue());
            gioHangChiTietRepository.save(gioHangChiTiet.get());
            Optional<GioHang> gioHang = gioHangRepository.findById(gioHangChiTiet.get().getGioHang().getId());
            gioHang.get().setTrangThai(StatusCartEnums.DA_THANH_TOAN.getValue());
            gioHangRepository.save(gioHang.get());
            if (gioHangChiTiet.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setId(UUID.randomUUID());
                hoaDonChiTiet.setHoaDon(hoaDon.get());
                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());
                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.get().getSanPhamChiTiet().getId()).get();
                System.out.println(gioHangChiTiet.get().getSanPhamChiTiet().getId());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.get().getSoLuong());
                chiTietSanPhamRepository.save(sanPhamChiTiet);
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

    @Override
    public HoaDon updateHoaDon(UUID hoaDonId, String name) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoaDonId);
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findByUsername(name);
            if (taiKhoanOptional.isPresent()) {
                TaiKhoan taiKhoan = taiKhoanOptional.get();
                hoaDon.setTaiKhoanNhanVien(taiKhoan);
            }
            return hoaDonRepository.save(hoaDon); // Lưu hóa đơn đã cập nhật và trả về nó
        }
        return null; // Xử lý khi hóa đơn không tồn tại
    }

}

