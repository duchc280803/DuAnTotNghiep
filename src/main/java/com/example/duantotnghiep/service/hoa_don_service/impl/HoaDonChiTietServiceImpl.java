package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.config.VnPayConfigTaiQuay;
import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.TraHangRequest;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.hoa_don_service.HoaDonChiTietService;
import com.example.duantotnghiep.util.FormatNumber;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private AuditLogService auditLogService;

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

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Autowired
    private VoucherRepository voucherRepository;

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
    public MessageResponse themSanPhamVaoHoaDonChiTiet(UUID idHoaDon, UUID idSanPhamChiTiet, int soLuong, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> findByHoaDon = hoaDonRepository.findById(idHoaDon);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
        if (findByHoaDon.isEmpty()) {
            return MessageResponse.builder().message("Hóa Đơn Null").build();
        }
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(idSanPhamChiTiet).get();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonAndSanPhamChiTiet_Id(findByHoaDon.get(), idSanPhamChiTiet);


        // Truyền vào hóa đơn chi tiết id hóa đơn và id sản phẩm chi tiết

        if (hoaDonChiTiet != null) {
            hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() + soLuong);
            hoaDonChiTiet.setDonGiaSauGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
            auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), findByHoaDon.get().getMa(), "Thêm sản phẩm", findByHoaDon.get().getMa(),
                    "Mã sản phẩm: " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getMaSanPham(),
                    "Tên sản phẩm: " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuong, "");
        } else {
            hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setId(UUID.randomUUID());
            hoaDonChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
            hoaDonChiTiet.setHoaDon(findByHoaDon.get());
            hoaDonChiTiet.setSoLuong(soLuong);
            hoaDonChiTiet.setDonGia(sanPhamChiTiet.getSanPham().getGiaBan());
            hoaDonChiTiet.setDonGiaSauGiam(sanPhamChiTiet.getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(sanPhamChiTiet.getSanPham().getId()))));
            hoaDonChiTiet.setTrangThai(1);
            auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), findByHoaDon.get().getMa(), "Thêm sản phẩm", findByHoaDon.get().getMa(),
                    "Mã sản phẩm: " + sanPhamChiTiet.getSanPham().getMaSanPham(),
                    "Tên sản phẩm: " + sanPhamChiTiet.getSanPham().getTenSanPham(),
                    "Số lượng: " + soLuong + "", "");
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

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setHoaDon(findByHoaDon.get());
        trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(findByHoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
        trangThaiHoaDonRepository.save(trangThaiHoaDon);

        chiTietSanPhamRepository.save(sanPhamChiTiet);

        BigDecimal tongTien = BigDecimal.ZERO;
        for (HoaDonChiTiet x : findByHoaDon.get().getHoaDonChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaSauGiam().multiply(new BigDecimal(x.getSoLuong())));
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
        findByHoaDon.get().setVoucher(selectedVoucher);
        findByHoaDon.get().setTienGiamGia(new BigDecimal(maxDiscount));
        hoaDonRepository.save(findByHoaDon.get());
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), findByHoaDon.get().getMa(), "Cập nhật voucher", findByHoaDon.get().getMa(), selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi, String username) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findById(idHoaDonChiTiet);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
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

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDonChiTietOptional.get().getHoaDon().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        BigDecimal tongTien = BigDecimal.ZERO;
        for (HoaDonChiTiet x : hoaDon.get().getHoaDonChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaSauGiam().multiply(new BigDecimal(x.getSoLuong())));
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
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật số lượng", hoaDon.get().getMa(),
                "Mã sản phẩm: " + hoaDonChiTietOptional.get().getSanPhamChiTiet().getSanPham().getMaSanPham(),
                "Tên sản phẩm: " + hoaDonChiTietOptional.get().getSanPhamChiTiet().getSanPham().getTenSanPham(),
                +soLuongMoi + "", "");
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(), selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");


    }

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws CsvValidationException, IOException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);

        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(timestamp);
        loaiHinhThucThanhToan.setTenLoai(transactionRequest.getTenLoai());
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(timestamp);
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien(transactionRequest.getSoTien());
        hinhThucThanhToan.setGhiChu(transactionRequest.getGhiChu());
        hinhThucThanhToan.setPhuongThucThanhToan(transactionRequest.getTrangThai());
        hinhThucThanhToan.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);

        auditLogService.writeAuditLogHoadon(taiKhoanUser.getMaTaiKhoan(),  hoaDon.get().getMa(), "Xác nhận thanh toán", hoaDon.get().getMa(), "Số tiền: " + transactionRequest.getSoTien(), "Thanh toán: " + (transactionRequest.getTrangThai() == 1 ? "Tiền mặt" : "Chuyển khoản"), "", "");

        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

    @Override
    public MessageResponse comfirmStatusHuyDon(UUID idHoaDon, TrangThaiHoaDonRequest request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        hoaDon.setNgayCapNhap(timestamp);
        hoaDon.setTrangThai(request.getNewTrangThai());
        hoaDonRepository.save(hoaDon);

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(request.getNewTrangThai());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDon.setGhiChu(request.getGhiChu());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);

        for (HoaDonChiTiet x : hoaDon.getHoaDonChiTietList()) {
            SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(x.getSanPhamChiTiet().getId()).get();
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + x.getSoLuong());
            chiTietSanPhamRepository.save(sanPhamChiTiet);
        }
        HinhThucThanhToan hinhThucThanhToan = hinhThucThanhToanRepository.findByHoaDon(hoaDon);
        if (hinhThucThanhToan != null) {
            LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
            loaiHinhThucThanhToan.setId(UUID.randomUUID());
            loaiHinhThucThanhToan.setNgayTao(timestamp);
            loaiHinhThucThanhToan.setTenLoai("Nhân viên hoàn tiền");
            loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

            HinhThucThanhToan hoanTienChoKhach = new HinhThucThanhToan();
            hoanTienChoKhach.setId(UUID.randomUUID());
            hoanTienChoKhach.setTongSoTien(hoaDon.getThanhTien());
            hoanTienChoKhach.setNgayThanhToan(timestamp);
            hoanTienChoKhach.setHoaDon(hoaDon);
            hoanTienChoKhach.setGhiChu("Hoàn tiền");
            hoanTienChoKhach.setTaiKhoan(hoaDon.getTaiKhoanKhachHang());
            hoanTienChoKhach.setTrangThai(1);
            hoanTienChoKhach.setPhuongThucThanhToan(1);
            hoanTienChoKhach.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
            hoanTienChoKhach.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
            hinhThucThanhToanRepository.save(hoanTienChoKhach);
        }
        return MessageResponse.builder().message("Update thành công").build();
    }

    @Override
    public MessageResponse rollBackOrder(UUID idHoaDon, TrangThaiHoaDonRequest request) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        hoaDon.setTrangThai(request.getNewTrangThai());
        hoaDonRepository.save(hoaDon);
        for (HoaDonChiTiet hoaDonChiTiet : hoaDon.getHoaDonChiTietList()) {
            hoaDonChiTiet.setTrangThai(request.getNewTrangThai());
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }
        return null;
    }

    @Override
    public HoaDon findByHoaDon(UUID id) {
        return hoaDonRepository.findById(id).get();
    }

    @Override
    public ProductDetailDTOResponse getDetailSanPham(UUID idhdct) {
        return hoaDonChiTietRepository.getDetailSanPham(idhdct);
    }

    @Override
    public MessageResponse createOrUpdate(UUID id, UUID idhdct, TraHangRequest traHangRequest, String username) throws IOException, CsvValidationException {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idhdct).get();
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).get();
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(hoaDonChiTiet.getSanPhamChiTiet().getId()).orElse(null);
        SanPham sanPhamHoaDon = sanPhamRepository.findById(sanPhamChiTiet.getSanPham().getId()).orElse(null);
        Voucher voucher = null;
        if (hoaDon.getVoucher() != null) {
            voucher = voucherRepository.findById(hoaDon.getVoucher().getId()).get();
        }
        List<SanPhamHoaDonChiTietResponse> productInHoaDon = hoaDonChiTietRepository.getSanPhamHDCT(hoaDonChiTiet.getHoaDon().getId());
        int count = 0;


        for (SanPhamHoaDonChiTietResponse sanPham : productInHoaDon) {
            if (sanPham.getTrangThai() == 5) {
                count++;
            }
        }
        // trả hàng
        prepareOrderDetails(hoaDon, traHangRequest, hoaDonChiTiet, sanPhamChiTiet, count);

        if (hoaDon.getTienShip() == null) {
            hoaDon.setTienShip(BigDecimal.ZERO);
        }
        if (hoaDon.getTienGiamGia() == null) {
            hoaDon.setTienGiamGia(BigDecimal.ZERO);
        }
        BigDecimal tongTienSauKhiDaTraHang = BigDecimal.ZERO;
        List<SanPhamHoaDonChiTietResponse> checkGia = hoaDonChiTietRepository.getSanPhamHDCT(id);
        for (SanPhamHoaDonChiTietResponse sanPham : checkGia) {
            if (sanPham.getTrangThai() == 5) {
                tongTienSauKhiDaTraHang = tongTienSauKhiDaTraHang.add(sanPham.getDonGiaSauGiam().multiply(BigDecimal.valueOf(sanPham.getSoLuong())));
            }
        }

        // Save lại voucher
        applyVoucherAndPayment(hoaDon, voucher, tongTienSauKhiDaTraHang);
        System.out.println("Đầu tiên" + hoaDon.getThanhTien());

        BigDecimal tienSauKhiTra = tongTienSauKhiDaTraHang.add(hoaDon.getTienShip()).subtract(hoaDon.getTienGiamGia());
        System.out.println("Thứ 2 " + hoaDon.getThanhTien());
        // Hoàn tiền
        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new java.sql.Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai("Nhân viên hoàn tiền");
        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(hoaDon.getTaiKhoanKhachHang());
        hinhThucThanhToan.setTongSoTien(hoaDon.getThanhTien().subtract(tienSauKhiTra));
        hinhThucThanhToan.setGhiChu("Nhân viên hoàn tiền cho khách");
        hinhThucThanhToan.setPhuongThucThanhToan(1);
        hinhThucThanhToan.setCodeTransaction(VnPayConfigTaiQuay.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon);
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hoaDon.setThanhTien(tongTienSauKhiDaTraHang.add(hoaDon.getTienShip()).subtract(hoaDon.getTienGiamGia()));
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(),  hoaDon.getMa(), "Trả hàng", hoaDon.getMa(), "Mã sản phẩm: " + sanPhamHoaDon.getMaSanPham(), "Tên sản phẩm: " + sanPhamHoaDon.getTenSanPham(), "Số lượng trả: " + traHangRequest.getSoLuong().toString(), "");
        return MessageResponse.builder().message("Trả hàng thành công").build();
    }

    /**
     * Trả hàng
     *
     * @param hoaDon
     * @param traHangRequest
     * @param hoaDonChiTiet
     * @param sanPhamChiTiet
     * @param count
     */
    public void prepareOrderDetails(HoaDon hoaDon, TraHangRequest traHangRequest, HoaDonChiTiet hoaDonChiTiet, SanPhamChiTiet sanPhamChiTiet, int count) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDonChiTiet addTraHang = new HoaDonChiTiet();
        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        if (count == 1) {
            if (traHangRequest.getSoLuong() == hoaDonChiTiet.getSoLuong()) {
                hoaDonChiTiet.setTrangThai(7);
                hoaDonChiTiet.setComment(traHangRequest.getGhiChu());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                trangThaiHoaDon.setId(UUID.randomUUID());
                trangThaiHoaDon.setTrangThai(7);
                trangThaiHoaDon.setThoiGian(timestamp);
                trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                trangThaiHoaDon.setUsername(hoaDon.getTaiKhoanNhanVien().getMaTaiKhoan());
                trangThaiHoaDon.setHoaDon(hoaDon);
            } else {
                addTraHang.setId(UUID.randomUUID());
                addTraHang.setComment(traHangRequest.getGhiChu());
                addTraHang.setDonGia(hoaDonChiTiet.getDonGia());
                addTraHang.setTrangThai(7);
                addTraHang.setHoaDon(hoaDonChiTiet.getHoaDon());
                addTraHang.setSanPhamChiTiet(hoaDonChiTiet.getSanPhamChiTiet());
                addTraHang.setDonGiaSauGiam(hoaDonChiTiet.getDonGiaSauGiam());
                addTraHang.setSoLuong(traHangRequest.getSoLuong());
                hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() - traHangRequest.getSoLuong());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                hoaDonChiTietRepository.save(addTraHang);
                trangThaiHoaDon.setId(UUID.randomUUID());
                trangThaiHoaDon.setTrangThai(7);
                trangThaiHoaDon.setThoiGian(timestamp);
                trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                trangThaiHoaDon.setHoaDon(hoaDon);
                trangThaiHoaDon.setUsername(hoaDon.getTaiKhoanNhanVien().getMaTaiKhoan());
            }
        }
        if (count > 1) {
            if (hoaDonChiTiet.getSoLuong() == traHangRequest.getSoLuong()) {
                hoaDonChiTiet.setTrangThai(7);
                hoaDonChiTiet.setComment(traHangRequest.getGhiChu());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                trangThaiHoaDon.setId(UUID.randomUUID());
                trangThaiHoaDon.setTrangThai(7);
                trangThaiHoaDon.setThoiGian(timestamp);
                trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                trangThaiHoaDon.setHoaDon(hoaDon);
                trangThaiHoaDon.setUsername(hoaDon.getTaiKhoanNhanVien().getMaTaiKhoan());
            } else {
                addTraHang.setId(UUID.randomUUID());
                addTraHang.setComment(traHangRequest.getGhiChu());
                addTraHang.setDonGia(hoaDonChiTiet.getDonGia());
                addTraHang.setTrangThai(7);
                addTraHang.setHoaDon(hoaDonChiTiet.getHoaDon());
                addTraHang.setSanPhamChiTiet(hoaDonChiTiet.getSanPhamChiTiet());
                addTraHang.setDonGiaSauGiam(hoaDonChiTiet.getDonGiaSauGiam());
                addTraHang.setSoLuong(traHangRequest.getSoLuong());
                hoaDonChiTietRepository.save(addTraHang);
                hoaDonChiTiet.setSoLuong(hoaDonChiTiet.getSoLuong() - traHangRequest.getSoLuong());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                trangThaiHoaDon.setId(UUID.randomUUID());
                trangThaiHoaDon.setTrangThai(7);
                trangThaiHoaDon.setThoiGian(timestamp);
                trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                trangThaiHoaDon.setHoaDon(hoaDon);
                trangThaiHoaDon.setUsername(hoaDon.getTaiKhoanNhanVien().getMaTaiKhoan());
            }
        }
        hoaDonChiTietRepository.save(hoaDonChiTiet);
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        chiTietSanPhamRepository.save(sanPhamChiTiet);
    }

    /**
     * Tính lại app voucher
     *
     * @param hoaDon
     * @param voucher
     * @param ktVoucher
     */
    @Override
    public void applyVoucherAndPayment(HoaDon hoaDon, Voucher voucher, BigDecimal ktVoucher) {
        if (voucher != null) {
            if (ktVoucher.longValue() < voucher.getGiaTriToiThieuDonhang()) {
                List<Voucher> voucherList = voucherRepository.getAllVoucher();
                Long maxDiscount = 0L; // Khởi tạo giá trị giảm giá lớn nhất ban đầu là 0
                Voucher selectedVoucher = null; // Biến để lưu trữ voucher được chọn
                for (Voucher v : voucherList) {
                    if (ktVoucher.longValue() >= v.getGiaTriToiThieuDonhang()) {
                        if (v.getGiaTriGiam().compareTo(maxDiscount) > 0) { // So sánh giá trị giảm giá với maxDiscount
                            maxDiscount = v.getGiaTriGiam(); // Cập nhật maxDiscount nếu giá trị mới lớn hơn
                            selectedVoucher = v; // Lưu trữ voucher có giá trị giảm giá lớn nhất
                        }
                    } else {
                        hoaDon.setVoucher(null);
//                        hoaDon.setThanhTien(new BigDecimal(0));
                        hoaDon.setTienGiamGia(BigDecimal.ZERO);
                    }
                }
                if (selectedVoucher != null) {
                    hoaDon.setVoucher(selectedVoucher); // Áp dụng voucher có giảm giá lớn nhất cho hoaDon
                    if (selectedVoucher.getHinhThucGiam() == 1) {
                        Long giaTriGiam = selectedVoucher.getGiaTriGiam(); // Lấy giá trị giảm giá từ selectedVoucher
                        double giaTriGiamDouble = giaTriGiam.doubleValue() / 100.0; // Nhân giá trị giảm giá với 100

                        hoaDon.setTienGiamGia(new BigDecimal(giaTriGiamDouble).multiply(ktVoucher));
                    }
                    if (selectedVoucher.getHinhThucGiam() == 2) {
                        hoaDon.setTienGiamGia(new BigDecimal(selectedVoucher.getGiaTriGiam()));
                    }
                }
            }
        }

        if (voucher == null) {
            List<Voucher> voucherList = voucherRepository.getAllVoucher();
            Long maxDiscount = 0L; // Khởi tạo giá trị giảm giá lớn nhất ban đầu là 0
            Voucher selectedVoucher = null; // Biến để lưu trữ voucher được chọn
            for (Voucher v : voucherList) {
                if (ktVoucher.longValue() >= v.getGiaTriToiThieuDonhang()) {
                    if (v.getGiaTriGiam().compareTo(maxDiscount) > 0) { // So sánh giá trị giảm giá với maxDiscount
                        maxDiscount = v.getGiaTriGiam(); // Cập nhật maxDiscount nếu giá trị mới lớn hơn
                        selectedVoucher = v; // Lưu trữ voucher có giá trị giảm giá lớn nhất
                    }
                } else {
                    hoaDon.setVoucher(null);
//                    hoaDon.setThanhTien(new BigDecimal(0));
                    hoaDon.setTienGiamGia(BigDecimal.ZERO);
                }
            }
            if (selectedVoucher != null) {
                hoaDon.setVoucher(selectedVoucher); // Áp dụng voucher có giảm giá lớn nhất cho hoaDon
                if (selectedVoucher.getHinhThucGiam() == 1) {
                    Long giaTriGiam = selectedVoucher.getGiaTriGiam(); // Lấy giá trị giảm giá từ selectedVoucher
                    double giaTriGiamDouble = giaTriGiam.doubleValue() / 100.0; // Nhân giá trị giảm giá với 100

                    hoaDon.setTienGiamGia(new BigDecimal(giaTriGiamDouble).multiply(ktVoucher));
                }
                if (selectedVoucher.getHinhThucGiam() == 2) {
                    hoaDon.setTienGiamGia(new BigDecimal(selectedVoucher.getGiaTriGiam()));
                }
            }
        }
    }

    /**
     * Hàm hoàn tiền cho khách
     *
     * @param hoaDon
     * @param tongTienSauTra
     */
    @Override
    public void savePaymentDetails(HoaDon hoaDon, BigDecimal tongTienSauTra) {

    }

    /**
     * Xóa sản phẩm khỏi hóa đơn chi tiết
     *
     * @param id
     * @param username
     * @throws IOException
     * @throws CsvValidationException
     */
    @Override
    public void deleteOrderDetail(UUID idHoaDon, UUID id, String username) throws IOException, CsvValidationException {
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id).orElse(null);
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).orElse(null);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(), hoaDon.get().getMa(), "Xóa sản phẩm", hoaDon.get().getMa(), "Mã sản phẩm: " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getMaSanPham(), "Tên sản phẩm: " + hoaDonChiTiet.getSanPhamChiTiet().getSanPham().getTenSanPham(), "", "");
        hoaDonChiTietRepository.deleteById(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (hoaDon.isEmpty()) {
            System.out.printf("Null");
        }
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


        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");

        BigDecimal tongTien = BigDecimal.ZERO;
        for (HoaDonChiTiet x : hoaDon.get().getHoaDonChiTietList()) {
            tongTien = tongTien.add(x.getDonGiaSauGiam().multiply(new BigDecimal(x.getSoLuong())));
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
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        auditLogService.writeAuditLogHoadon(taiKhoan.getMaTaiKhoan(),  hoaDon.get().getMa(), "Cập nhật voucher", hoaDon.get().getMa(), selectedVoucher == null ? "Không áp dụng" : "Mã voucher:  " + selectedVoucher.getMaVoucher(),
                selectedVoucher == null ? "" : "Giá trị giảm: " + FormatNumber.formatBigDecimal(new BigDecimal(maxDiscount)) + "đ", "", "");

    }

    @Override
    public boolean traHang(UUID id) {
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon_Id(id);
        for (HoaDonChiTiet x : hoaDonChiTiet) {
            if (x.getTrangThai() == 7) {
                return true;
            }
        }
        return false;
    }

    @Override
    public OrderDetailUpdate orderDetailUpdate(UUID id) {
        return hoaDonRepository.orderDetailUpdate(id);
    }


    @Override
    public List<NhanVienOrderResponse> taiKhoanList() {
        return khachHangRepository.listNv();
    }

    @Override
    public MessageResponse updateNhanVien(UUID idHoaDon, UUID idNhanVien) {
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<TaiKhoan> nhanVien = khachHangRepository.findById(idNhanVien);
        hoaDon.get().setTaiKhoanNhanVien(nhanVien.get());
        hoaDonRepository.save(hoaDon.get());
        return MessageResponse.builder().message("Update thành công").build();
    }

    @Override
    public BigDecimal tongTienHang(UUID id) {
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findById(id);
        if (hoaDonOptional.isEmpty()) {
            // Xử lý trường hợp không tìm thấy hoá đơn với ID cụ thể
            return BigDecimal.ZERO;
        }

        HoaDon hoaDon = hoaDonOptional.get();
        List<HoaDonChiTiet> hoaDonChiTiets = hoaDon.getHoaDonChiTietList();

        BigDecimal tongTien = BigDecimal.ZERO;
        for (HoaDonChiTiet hdct : hoaDonChiTiets) {
            if (hdct.getTrangThai() != 7) {
                BigDecimal donGiaSauGiam = hdct.getDonGiaSauGiam() != null ? hdct.getDonGiaSauGiam() : BigDecimal.ZERO;
                Integer soLuong = hdct.getSoLuong() != null ? hdct.getSoLuong() : 0;
                tongTien = tongTien.add(donGiaSauGiam.multiply(new BigDecimal(soLuong)));
            }
        }
        return tongTien;
    }


}
