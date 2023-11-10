package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.hoa_don_service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public ThongTinDonHang getThongTinDonHang(UUID idHoaDon) {
        return hoaDonChiTietRepository.getThongTinDonHang(idHoaDon);
    }

    @Override
    public List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon) {
        return hoaDonChiTietRepository.getSanPhamHDCT(idHoaDon);
    }

    @Override
    public List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon) {
        return hoaDonChiTietRepository.getLichSuThanhToan(idHoaDon);
    }

    @Override
    public List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(UUID idHoaDon) {
        return hoaDonChiTietRepository.getAllTrangThaiHoaDon(idHoaDon);
    }

    @Override
    public void confirmThanhToan(UUID hoadonId, XacNhanThanhToanRequest request) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(hoadonId);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        if (hoaDonOptional.isPresent()) {
            HoaDon hoaDon = hoaDonOptional.get();
            HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
            hinhThucThanhToan.setId(UUID.randomUUID());
            hinhThucThanhToan.setNgayThanhToan(timestamp);
            hinhThucThanhToan.setGhiChu(request.getGhiChu());
            hinhThucThanhToan.setTrangThai(request.getHinhThuc());
            hinhThucThanhToan.setHoaDon(hoaDon);
            hinhThucThanhToanRepository.save(hinhThucThanhToan);

        }
    }

    @Override
    public MoneyResponse getMoneyByHoaDon(UUID idHoaDon) {
        return hoaDonChiTietRepository.getAllMoneyByHoaDon(idHoaDon);
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long sumPriceTien = 0L;
        long sumPricePhanTram = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            long mucGiam = spGiamGia.getMucGiam();
            if (spGiamGia.getGiamGia().getHinhThucGiam() == 1) {
                sumPriceTien += mucGiam;
            }
            if (spGiamGia.getGiamGia().getHinhThucGiam() == 2) {
                long donGiaAsLong = spGiamGia.getDonGia().longValue();
                double giamGia = (double) mucGiam / 100;
                long giaTienSauGiamGia = (long) (donGiaAsLong * giamGia);
                sumPricePhanTram += giaTienSauGiamGia;
            }
        }
        return sumPriceTien + sumPricePhanTram;
    }

    @Override
    public MessageResponse themSanPhamVaoHoaDonChiTiet(UUID idHoaDon, UUID idSanPhamChiTiet, int soLuong) {
        Optional<HoaDon> findByHoaDon = hoaDonRepository.findById(idHoaDon);
        if (findByHoaDon.isEmpty()) {
            return MessageResponse.builder().message("Hóa Đơn Null").build();
        }
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(idSanPhamChiTiet).get();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonAndSanPhamChiTiet_Id(findByHoaDon.get(), idSanPhamChiTiet);
        if (hoaDonChiTiet != null) {
            hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + soLuong);
            hoaDonChiTiet.setDonGiaSauGiam(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId())));
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
        } else {
            hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(UUID.randomUUID());
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setHoaDon(findByHoaDon.get());
            hoaDonChiTiet.setSoLuong(soLuong);
            hoaDonChiTiet.setDonGia(sanPhamChiTiet.getSanPham().getGiaBan());
            hoaDonChiTiet.setDonGiaSauGiam(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId())));
            hoaDonChiTiet.setTrangThai(1);
        }
        chiTietSanPhamRepository.save(sanPhamChiTiet);
        hoaDonChiTietRepository.save(hoaDonChiTiet);
        return MessageResponse.builder().message("Thêm thành công").build();
    }


    public void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi) {
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findById(idHoaDonChiTiet);
        if (hoaDonChiTietOptional.isPresent()) {
            hoaDonChiTietOptional.get().setSoLuong(soLuongMoi);
            hoaDonChiTietRepository.save(hoaDonChiTietOptional.get());
        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }
    }
}
