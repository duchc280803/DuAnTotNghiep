package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.CartDetailCounterService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartDetailCounterServiceImpl implements CartDetailCounterService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        GioHang gioHang = gioHangRepository.findByGioHang(idGioHang);
        Optional<TaiKhoan> taiKhoanHoaDon = taiKhoanRepository.findById(gioHang.getTaiKhoan().getId());
        Optional<HoaDon> hoaDon = hoaDonRepository.findByTaiKhoanKhachHang(taiKhoanHoaDon.get());

        if (gioHang == null) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }
        GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHangAndSanPhamChiTiet_Id(gioHang, idSanPhamChiTiet);
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(idSanPhamChiTiet).get();
        if (ghct != null) {
            ghct.setSoLuong(ghct.getSoLuong() + soLuong);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
            auditLogService.writeAuditLogHoadon(username, taiKhoan.get().getEmail(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuong, "");
        } else {
            ghct = new GioHangChiTiet();
            ghct.setId(UUID.randomUUID());
            ghct.setGioHang(gioHang);
            ghct.setDonGia(sanPhamChiTiet.getSanPham().getGiaBan());
            ghct.setDonGiaKhiGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            sanPhamChiTiet.setId(idSanPhamChiTiet);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
            ghct.setSanPhamChiTiet(sanPhamChiTiet);
            auditLogService.writeAuditLogHoadon(username, taiKhoan.get().getEmail(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuong, "");

            ghct.setSoLuong(soLuong);
        }

        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.save(ghct);

        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse themSanPhamVaoGioHangChiTietQrCode(UUID idGioHang, String qrCode, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        GioHang gioHang = gioHangRepository.findByGioHang(idGioHang);
        Optional<TaiKhoan> taiKhoanHoaDon = taiKhoanRepository.findById(gioHang.getTaiKhoan().getId());
        Optional<HoaDon> hoaDon = hoaDonRepository.findByTaiKhoanKhachHang(taiKhoanHoaDon.get());
        if (gioHang == null) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }

        GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHang(gioHang);
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findByQrcode(qrCode);
        if (ghct != null) {
            ghct.setSoLuong(ghct.getSoLuong() + 1);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - 1);
            auditLogService.writeAuditLogHoadon(username, taiKhoan.get().getEmail(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: 1" , "");
        } else {
            ghct = new GioHangChiTiet();
            ghct.setId(UUID.randomUUID());
            ghct.setGioHang(gioHang);
            ghct.setDonGia(sanPhamChiTiet.getSanPham().getGiaBan());
            ghct.setDonGiaKhiGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            sanPhamChiTiet.setId(sanPhamChiTiet.getId());
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - 1);
            ghct.setSanPhamChiTiet(sanPhamChiTiet);
            ghct.setSoLuong(1);
            auditLogService.writeAuditLogHoadon(username, taiKhoan.get().getEmail(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: 1" , "");
        }

        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.save(ghct);

        return MessageResponse.builder().message("Thêm thành công").build();
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long sumPriceTien = 0L;
        long sumPricePhanTram = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);
        if (spGiamGiaList.isEmpty()) {
            return 0L;
        } else {
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
        }
        return sumPriceTien + sumPricePhanTram;
    }

    @Override
    public List<GioHangCustom> loadGH(UUID id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<GioHangCustom> gioHangCustomPage = gioHangChiTietRepository.loadOnGioHang(id, pageable);
        return gioHangCustomPage.getContent();
    }

    @Override
    public void deleteProductInCart(UUID id) {
        gioHangChiTietRepository.deleteById(id);
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(id).get();
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.getSanPhamChiTiet().getId()).get();
        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + gioHangChiTiet.getSoLuong());
        chiTietSanPhamRepository.save(sanPhamChiTiet);
    }

    @Override
    public List<GioHangChiTiet> getIdCartDetail(UUID idCart) {
        return gioHangChiTietRepository.findByGioHang_Id(idCart);
    }

    public void capNhatSoLuong(UUID idgiohangchitiet, int soLuongMoi, String username) throws IOException, CsvValidationException {
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idgiohangchitiet);

        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        GioHang gioHang = gioHangRepository.findByGioHang(optionalGioHangChiTiet.get().getGioHang().getId());
        Optional<TaiKhoan> taiKhoanHoaDon = taiKhoanRepository.findById(gioHang.getTaiKhoan().getId());
        Optional<HoaDon> hoaDon = hoaDonRepository.findByTaiKhoanKhachHang(taiKhoanHoaDon.get());
        Optional<SanPhamChiTiet> sanPhamChiTiet = chiTietSanPhamRepository.findById(optionalGioHangChiTiet.get().getSanPhamChiTiet().getId());

        if (optionalGioHangChiTiet.isPresent()) {
            optionalGioHangChiTiet.get().setSoLuong(soLuongMoi);
            gioHangChiTietRepository.save(optionalGioHangChiTiet.get());
            auditLogService.writeAuditLogHoadon(username, taiKhoan.get().getEmail(), "Cập nhật số lượng", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.get().getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.get().getSanPham().getTenSanPham(),
                    "Số lượng: " +soLuongMoi , "");

        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }
    }
}
