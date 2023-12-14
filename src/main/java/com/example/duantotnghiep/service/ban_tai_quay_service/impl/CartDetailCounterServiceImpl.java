package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.CartDetailCounterService;
import com.opencsv.exceptions.CsvValidationException;
import com.example.duantotnghiep.util.FormatNumber;
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
    private VoucherRepository voucherRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong, UUID idHoaDon, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        GioHang gioHang = gioHangRepository.findByGioHang(idGioHang);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        if (gioHang == null) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }
        GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHangAndSanPhamChiTiet_Id(gioHang, idSanPhamChiTiet);
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(idSanPhamChiTiet).get();
        if (ghct != null) {
            ghct.setSoLuong(ghct.getSoLuong() + soLuong);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
            auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Thêm sản phẩm", hoaDon.get().getMa(),
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
            auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuong, "");

            ghct.setSoLuong(soLuong);
        }

        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.save(ghct);

        BigDecimal tongTien = BigDecimal.ZERO;
        for (GioHangChiTiet x : gioHang.getGioHangChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaKhiGiam().multiply(new BigDecimal(x.getSoLuong())));
        }
        Long maxDiscount = 0L;
        Long maxDiscount1 = 0L;
        Long maxDiscount2 = 0L;
        Voucher selectedVoucher = null;
        Double giaTriGiamPhanTram = 0.0;

        for (Voucher v : voucherRepository.getAllVoucher()) {
            if (tongTien.longValue() >= v.getGiaTriToiThieuDonhang() && v.getGiaTriGiam() > maxDiscount) {
                if (v.getHinhThucGiam() == 1) {
                    Long giaTriGiam = v.getGiaTriGiam();
                    giaTriGiamPhanTram = giaTriGiam / 100.0;
                    maxDiscount1 = tongTien.multiply(new BigDecimal(giaTriGiamPhanTram)).longValue();
                    selectedVoucher = v;
                }

                if (v.getHinhThucGiam() == 2) {
                    maxDiscount2 = v.getGiaTriGiam();
                    selectedVoucher = v;
                }
            }
        }
        if (maxDiscount1 > maxDiscount2) {
            maxDiscount = maxDiscount1;
        } else {
            maxDiscount = maxDiscount2;
        }
        hoaDon.get().setVoucher(selectedVoucher);
        hoaDon.get().setTienGiamGia(new BigDecimal(maxDiscount));
        hoaDonRepository.save(hoaDon.get());
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(),  selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");
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

        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findByQrcode(qrCode);
        GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHangAndSanPhamChiTiet_Id(gioHang, sanPhamChiTiet.getId());
        if (ghct != null) {
            ghct.setSoLuong(ghct.getSoLuong() + 1);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - 1);
            auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: 1", "");
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
            auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Thêm sản phẩm", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: 1", "");
        }

        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.save(ghct);

        BigDecimal tongTien = BigDecimal.ZERO;
        for (GioHangChiTiet x : gioHang.getGioHangChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaKhiGiam().multiply(new BigDecimal(x.getSoLuong())));
        }
        Long maxDiscount = 0L;
        Long maxDiscount1 = 0L;
        Long maxDiscount2 = 0L;
        Voucher selectedVoucher = null;
        Double giaTriGiamPhanTram = 0.0;

        for (Voucher v : voucherRepository.getAllVoucher()) {
            if (tongTien.longValue() >= v.getGiaTriToiThieuDonhang() && v.getGiaTriGiam() > maxDiscount) {
                if (v.getHinhThucGiam() == 1) {
                    Long giaTriGiam = v.getGiaTriGiam();
                    giaTriGiamPhanTram = giaTriGiam / 100.0;
                    maxDiscount1 = tongTien.multiply(new BigDecimal(giaTriGiamPhanTram)).longValue();
                    selectedVoucher = v;
                }

                if (v.getHinhThucGiam() == 2) {
                    maxDiscount2 = v.getGiaTriGiam();
                    selectedVoucher = v;
                }
            }
        }
        if (maxDiscount1 > maxDiscount2) {
            maxDiscount = maxDiscount1;
        } else {
            maxDiscount = maxDiscount2;
        }
        hoaDon.get().setVoucher(selectedVoucher);
        hoaDon.get().setTienGiamGia(new BigDecimal(maxDiscount));
        hoaDonRepository.save(hoaDon.get());
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(),  selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");

        return MessageResponse.builder().message("Thêm thành công").build();
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long tongTienGiam = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            // Cập nhật tổng tiền giảm đúng cách, không khai báo lại biến tongTienGiam
            if (spGiamGia.getGiaGiam() == null) {
                return tongTienGiam;
            }
            if (spGiamGia.getGiaGiam() != null) {
                tongTienGiam += spGiamGia.getGiaGiam().longValue();
            }

        }

        return tongTienGiam;
    }

    @Override
    public List<GioHangCustom> loadGH(UUID id, Integer pageNumber, Integer pageSize) {
        List<GioHangCustom> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> gioHangCustomPage = gioHangChiTietRepository.loadOnGioHang(id, pageable);
        for (Object[] result : gioHangCustomPage.getContent()) {
            UUID idGh = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            Integer size = (Integer) result[6];
            String mauSac = (String) result[7];
            String chatLieu = (String) result[8];
            BigDecimal tongTien = giaGiam.multiply(new BigDecimal(soLuong));

            GioHangCustom chiTietSanPhamCustom = new GioHangCustom(
                    idGh, imgage, tenSanPham, giaBan, giaGiam, soLuong, size, mauSac, chatLieu, tongTien);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public void deleteProductInCart(UUID id, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = taiKhoanRepository.findByUsername(username);
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findById(id).get();
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.getSanPhamChiTiet().getId()).get();

        GioHang gioHang = gioHangRepository.findByGioHang(gioHangChiTiet.getGioHang().getId());
        Optional<TaiKhoan> khachHang = taiKhoanRepository.findById(gioHang.getTaiKhoan().getId());
        Optional<HoaDon> hoaDon = hoaDonRepository.findByTaiKhoanKhachHang(khachHang.get());

        sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + gioHangChiTiet.getSoLuong());
        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.deleteById(id);
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Xóa sản phẩm", hoaDon.get().getMa(),
                "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                "", "");
        BigDecimal tongTien = BigDecimal.ZERO;
        for (GioHangChiTiet x : gioHang.getGioHangChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaKhiGiam().multiply(new BigDecimal(x.getSoLuong())));
        }
        Long maxDiscount = 0L;
        Long maxDiscount1 = 0L;
        Long maxDiscount2 = 0L;
        Voucher selectedVoucher = null;
        Double giaTriGiamPhanTram = 0.0;

        for (Voucher v : voucherRepository.getAllVoucher()) {
            if (tongTien.longValue() >= v.getGiaTriToiThieuDonhang() && v.getGiaTriGiam() > maxDiscount) {
                if (v.getHinhThucGiam() == 1) {
                    Long giaTriGiam = v.getGiaTriGiam();
                    giaTriGiamPhanTram = giaTriGiam / 100.0;
                    maxDiscount1 = tongTien.multiply(new BigDecimal(giaTriGiamPhanTram)).longValue();
                    selectedVoucher = v;
                }

                if (v.getHinhThucGiam() == 2) {
                    maxDiscount2 = v.getGiaTriGiam();
                    selectedVoucher = v;
                }
            }
        }
        if (maxDiscount1 > maxDiscount2) {
            maxDiscount = maxDiscount1;
        } else {
            maxDiscount = maxDiscount2;
        }
        hoaDon.get().setVoucher(selectedVoucher);
        hoaDon.get().setTienGiamGia(new BigDecimal(maxDiscount));
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(),  selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");
        hoaDonRepository.save(hoaDon.get());
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), taiKhoan.get().getEmail(), selectedVoucher == null ? "Không có voucher" : selectedVoucher.getMaVoucher(), hoaDon.get().getMa(),
                "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                "", "");
    }

    @Override
    public List<GioHangChiTiet> getIdCartDetail(UUID idCart) {
        return gioHangChiTietRepository.findByGioHang_Id(idCart);
    }

    @Override
    public String tongTienHang(UUID id) {
        BigDecimal tongTien = BigDecimal.ZERO;
        List<Object[]> gioHangCustomPage = gioHangChiTietRepository.tongTien(id);
        for (Object[] result : gioHangCustomPage) {
            BigDecimal giaGiam = (BigDecimal) result[0];
            Integer soLuong = (Integer) result[1];
            tongTien = tongTien.add(giaGiam.multiply(new BigDecimal(soLuong)));
        }
        return FormatNumber.formatBigDecimal(tongTien);
    }

    @Override
    public List<GioHangCustom> loadGHTien(UUID id) {
        List<GioHangCustom> resultList = new ArrayList<>();
        List<Object[]> gioHangCustomPage = gioHangChiTietRepository.loadOnGioHangTien(id);
        for (Object[] result : gioHangCustomPage) {
            UUID idGh = (UUID) result[0];
            String imgage = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            BigDecimal giaGiam = (BigDecimal) result[4];
            Integer soLuong = (Integer) result[5];
            Integer size = (Integer) result[6];
            String mauSac = (String) result[7];
            String chatLieu = (String) result[8];
            BigDecimal tongTien = giaGiam.multiply(new BigDecimal(soLuong));

            GioHangCustom chiTietSanPhamCustom = new GioHangCustom(
                    idGh, imgage, tenSanPham, giaBan, giaGiam, soLuong, size, mauSac, chatLieu, tongTien);
            resultList.add(chiTietSanPhamCustom);
        }
        return resultList;
    }

    @Override
    public BigDecimal tongTienSpHoaDon(UUID id) {
        BigDecimal tongTien = BigDecimal.ZERO;
        GioHang gioHang = gioHangRepository.findByGioHang(id);
        for (GioHangChiTiet x : gioHang.getGioHangChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaKhiGiam().multiply(new BigDecimal(x.getSoLuong())));
        }
        return tongTien;
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
            auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật số lượng", hoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.get().getSanPham().getMaSanPham(), "Tên sản phẩm: " + sanPhamChiTiet.get().getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuongMoi, "");

        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }
        BigDecimal tongTien = BigDecimal.ZERO;
        for (GioHangChiTiet x : gioHang.getGioHangChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaKhiGiam().multiply(new BigDecimal(x.getSoLuong())));
        }
        Long maxDiscount = 0L;
        Long maxDiscount1 = 0L;
        Long maxDiscount2 = 0L;
        Voucher selectedVoucher = null;
        Double giaTriGiamPhanTram = 0.0;

        for (Voucher v : voucherRepository.getAllVoucher()) {
            if (tongTien.longValue() >= v.getGiaTriToiThieuDonhang() && v.getGiaTriGiam() > maxDiscount) {
                if (v.getHinhThucGiam() == 1) {
                    Long giaTriGiam = v.getGiaTriGiam();
                    giaTriGiamPhanTram = giaTriGiam / 100.0;
                    maxDiscount1 = tongTien.multiply(new BigDecimal(giaTriGiamPhanTram)).longValue();
                    selectedVoucher = v;
                }

                if (v.getHinhThucGiam() == 2) {
                    maxDiscount2 = v.getGiaTriGiam();
                    selectedVoucher = v;
                }
            }
        }
        if (maxDiscount1 > maxDiscount2) {
            maxDiscount = maxDiscount1;
        } else {
            maxDiscount = maxDiscount2;
        }
        hoaDon.get().setVoucher(selectedVoucher);
        hoaDon.get().setTienGiamGia(new BigDecimal(maxDiscount));
        hoaDonRepository.save(hoaDon.get());
        auditLogService.writeAuditLogHoadon(taiKhoan.get().getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(),  selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");
    }
}
