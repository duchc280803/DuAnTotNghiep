package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.config.VnPayConfig;
import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.hoa_don_service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
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

    @Autowired
    private KhachHangRepository khachHangRepository;

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
            hoaDonChiTiet.setDonGiaSauGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
        } else {
            hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(UUID.randomUUID());
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setHoaDon(findByHoaDon.get());
            hoaDonChiTiet.setSoLuong(soLuong);
            hoaDonChiTiet.setDonGia(sanPhamChiTiet.getSanPham().getGiaBan());
            hoaDonChiTiet.setDonGiaSauGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            hoaDonChiTiet.setTrangThai(1);
        }

        chiTietSanPhamRepository.save(sanPhamChiTiet);
        hoaDonChiTietRepository.save(hoaDonChiTiet);
        BigDecimal tongTienDonGia = BigDecimal.ZERO;
        BigDecimal tongTienDonGiaSauGIam = BigDecimal.ZERO;
        BigDecimal tongTienHang = BigDecimal.ZERO;
        for (HoaDonChiTiet hdct : findByHoaDon.get().getHoaDonChiTietList()) {
            if (hdct.getDonGiaSauGiam().compareTo(BigDecimal.ZERO) == 0) {
                tongTienDonGia = tongTienDonGia.add(hdct.getDonGia().multiply(new BigDecimal(hdct.getSoLuong())));
            }
            if (hdct.getDonGiaSauGiam().compareTo(BigDecimal.ZERO) != 0) {
                tongTienDonGiaSauGIam = tongTienDonGiaSauGIam.add(hdct.getDonGiaSauGiam().multiply(new BigDecimal(hdct.getSoLuong())));
            }
        }
        if (findByHoaDon.get().getTienShip() == null) {
            findByHoaDon.get().setThanhTien(tongTienHang.add(tongTienDonGia.add(tongTienDonGiaSauGIam).add(findByHoaDon.get().getTienGiamGia())));
        }
        if (findByHoaDon.get().getTienShip() != null) {
            findByHoaDon.get().setThanhTien(tongTienHang.add(tongTienDonGia.add(tongTienDonGiaSauGIam)).add(findByHoaDon.get().getTienGiamGia()).add(findByHoaDon.get().getTienShip()));
        }
        hoaDonRepository.save(findByHoaDon.get());
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi) {
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findById(idHoaDonChiTiet);
        if (hoaDonChiTietOptional.isPresent()) {
            hoaDonChiTietOptional.get().setSoLuong(soLuongMoi);
            hoaDonChiTietRepository.save(hoaDonChiTietOptional.get());
        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }

        Optional<HoaDon> hoaDon = hoaDonRepository.findById(hoaDonChiTietOptional.get().getHoaDon().getId());
        BigDecimal tongTienDonGia = BigDecimal.ZERO;
        BigDecimal tongTienDonGiaSauGIam = BigDecimal.ZERO;
        BigDecimal tongTienHang = BigDecimal.ZERO;
        for (HoaDonChiTiet hdct : hoaDon.get().getHoaDonChiTietList()) {
            if (hdct.getDonGiaSauGiam().compareTo(BigDecimal.ZERO) == 0) {
                tongTienDonGia = tongTienDonGia.add(hdct.getDonGia().multiply(new BigDecimal(hdct.getSoLuong())));
            }
            if (hdct.getDonGiaSauGiam().compareTo(BigDecimal.ZERO) != 0) {
                tongTienDonGiaSauGIam = tongTienDonGiaSauGIam.add(hdct.getDonGiaSauGiam().multiply(new BigDecimal(hdct.getSoLuong())));
            }
        }
        if (hoaDon.get().getTienShip() == null) {
            hoaDon.get().setThanhTien(tongTienHang.add(tongTienDonGia.add(tongTienDonGiaSauGIam).add(hoaDon.get().getTienGiamGia())));
        }
        if (hoaDon.get().getTienShip() != null) {
            hoaDon.get().setThanhTien(tongTienHang.add(tongTienDonGia.add(tongTienDonGiaSauGIam)).add(hoaDon.get().getTienGiamGia()).add(hoaDon.get().getTienShip()));
        }
        hoaDonRepository.save(hoaDon.get());
    }

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest) {
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien(transactionRequest.getSoTien());
        hinhThucThanhToan.setGhiChu(transactionRequest.getGhiChu());
        hinhThucThanhToan.setPhuongThucThanhToan(transactionRequest.getTrangThai());
        hinhThucThanhToan.setCodeTransaction(VnPayConfig.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        hoaDon.get().setTienKhachTra(transactionRequest.getSoTien());
        hoaDon.get().setTienThua(transactionRequest.getSoTien().subtract(hoaDon.get().getThanhTien()));
        hoaDonRepository.save(hoaDon.get());
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

}
