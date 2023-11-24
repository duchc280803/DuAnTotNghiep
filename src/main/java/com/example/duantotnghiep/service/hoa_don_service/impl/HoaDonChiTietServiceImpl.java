package com.example.duantotnghiep.service.hoa_don_service.impl;

import com.example.duantotnghiep.config.VnPayConfig;
import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.TraHangRequest;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.hoa_don_service.HoaDonChiTietService;
import com.opencsv.exceptions.CsvValidationException;
import org.bouncycastle.tsp.TSPUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
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
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setHoaDon(findByHoaDon.get());
        trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public void capNhatSoLuong(UUID idHoaDonChiTiet, int soLuongMoi) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
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

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDon.setTrangThai(StatusOrderEnums.CHINH_SUA_DON_HANG.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
    }

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest, String username) throws IOException, CsvValidationException {
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);

        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai(transactionRequest.getTenLoai());
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

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
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);

        auditLogService.writeAuditLogHoadonChiTiet("CREATE", username, taiKhoanUser.getEmail(), "Xác nhận thanh toán", hoaDon.get().getMa(), "Loại thanh toán: " + transactionRequest.getTenLoai(), "Số tiền: " + transactionRequest.getSoTien(), "Thanh toán: " + (transactionRequest.getTrangThai() == 1 ? "Tiền mặt" : "Chuyển khoản"));

        return MessageResponse.builder().message("Thanh toán thành công").build();
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
    public MessageResponse createOrUpdate(UUID idhdct, TraHangRequest traHangRequest, String username)  throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idhdct).orElse(null);
        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        TaiKhoan taiKhoan = taiKhoanRepository.findByUsername(username).get();

        if (hoaDonChiTiet != null) {
            SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(hoaDonChiTiet.getSanPhamChiTiet().getId()).orElse(null);
            HoaDon hoaDon = hoaDonRepository.findById(hoaDonChiTiet.getHoaDon().getId()).orElse(null);
            SanPham sanPhamHoaDon = sanPhamRepository.findById(sanPhamChiTiet.getSanPham().getId()).orElse(null);

            if (sanPhamChiTiet != null && hoaDon != null) {

                List<SanPhamHoaDonChiTietResponse> productInHoaDon = hoaDonChiTietRepository.getSanPhamHDCT(hoaDonChiTiet.getHoaDon().getId());
                int count = 0;
                BigDecimal tongTien = BigDecimal.ZERO;

                for (SanPhamHoaDonChiTietResponse sanPham : productInHoaDon) {
                    if (sanPham.getTrangThai() == 5) {
                        count++;                        }
                }
                if (count == 0) {
                    hoaDon.setTrangThai(6);
                } else {
                    if (count == 1){
                        if (traHangRequest.getSoLuong() == hoaDonChiTiet.getSoLuong()) {
                            hoaDonChiTiet.setTrangThai(7);
                            hoaDonChiTiet.setComment(traHangRequest.getGhiChu());
                            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                            trangThaiHoaDon.setId(UUID.randomUUID());
                            trangThaiHoaDon.setTrangThai(6);
                            trangThaiHoaDon.setThoiGian(timestamp);
                            trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                            trangThaiHoaDon.setHoaDon(hoaDon);
                            hoaDon.setTrangThai(6);
                            hoaDonChiTietRepository.save(hoaDonChiTiet);
                        }else {
                            HoaDonChiTiet addTraHang = new HoaDonChiTiet();
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
                            hoaDonChiTietRepository.save(hoaDonChiTiet);
                        }
                    }else {
                        if(hoaDonChiTiet.getSoLuong() == traHangRequest.getSoLuong()){
                            hoaDonChiTiet.setTrangThai(7);
                            hoaDonChiTiet.setComment(traHangRequest.getGhiChu());
                            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() + traHangRequest.getSoLuong());
                            trangThaiHoaDon.setId(UUID.randomUUID());
                            trangThaiHoaDon.setTrangThai(7);
                            trangThaiHoaDon.setThoiGian(timestamp);
                            trangThaiHoaDon.setGhiChu(traHangRequest.getGhiChu());
                            trangThaiHoaDon.setHoaDon(hoaDon);
                            hoaDonChiTietRepository.save(hoaDonChiTiet);
                        }else {
                            HoaDonChiTiet addTraHang = new HoaDonChiTiet();
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
                            hoaDonChiTietRepository.save(hoaDonChiTiet);

                        }
                    }
                }
                if (hoaDon.getTienShip() == null) {
                    hoaDon.setTienShip(BigDecimal.ZERO);
                }
                if (hoaDon.getTienGiamGia() == null) {
                    hoaDon.setTienGiamGia(BigDecimal.ZERO);
                }
                List<SanPhamHoaDonChiTietResponse> checkGia = hoaDonChiTietRepository.getSanPhamHDCT(hoaDonChiTiet.getHoaDon().getId());
                for (SanPhamHoaDonChiTietResponse sanPham : checkGia) {
                    if (sanPham.getTrangThai() == 5) {
                        tongTien = tongTien.add(sanPham.getDonGiaSauGiam().multiply(BigDecimal.valueOf(sanPham.getSoLuong())));
                    }
                }

                hoaDon.setThanhTien(tongTien.add(hoaDon.getTienShip()).subtract(hoaDon.getTienGiamGia()));
                chiTietSanPhamRepository.save(sanPhamChiTiet);
                trangThaiHoaDonRepository.save(trangThaiHoaDon);
                hoaDonRepository.save(hoaDon);
                auditLogService.writeAuditLogHoadonChiTiet("UPDATE", username, taiKhoan.getEmail(),"Trả hàng", hoaDon.getMa(), "Mã sản phẩm: "+ sanPhamHoaDon.getMaSanPham(), "Số lượng trả: " + traHangRequest.getSoLuong().toString(), "");

                return MessageResponse.builder().message("Trả hàng thành công").build();
            }
        }

        return MessageResponse.builder().message("Trả hàng thất bại").build();
    }

    @Override
    public void deleteOrderDetail(UUID id) {
        hoaDonChiTietRepository.deleteById(id);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDonChiTiet> hoaDonChiTietOptional = hoaDonChiTietRepository.findById(id);

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
        trangThaiHoaDon.setGhiChu("Nhân viên sửa đơn cho khách");
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
    }

    @Override
    public boolean traHang(UUID id) {
        List<HoaDonChiTiet> hoaDonChiTiet = hoaDonChiTietRepository.findByHoaDon_Id(id);
        for (HoaDonChiTiet x: hoaDonChiTiet){
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

}
