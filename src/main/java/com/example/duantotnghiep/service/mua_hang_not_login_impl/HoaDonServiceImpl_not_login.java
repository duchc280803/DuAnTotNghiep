package com.example.duantotnghiep.service.mua_hang_not_login_impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.StatusOrderEnums;
import com.example.duantotnghiep.enums.TypeOrderEnums;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.*;
import com.example.duantotnghiep.request.not_login.CreateKhachRequest_not_login;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.mua_hang_not_login_service.HoaDonService_not_login;
import com.example.duantotnghiep.util.SendEmailOrder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class HoaDonServiceImpl_not_login implements HoaDonService_not_login {

    @Autowired
    private HoaDonRepository_not_login hoaDonRepository;

    @Autowired
    private KhachHangRepository_not_login khachHangRepository_not_login;

    @Autowired
    private GioHangChiTietRepository_not_login gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository_not_login hoaDonChiTietRepository;

    @Autowired
    private GioHangRepository_not_login gioHangRepository_not_login;

    @Autowired
    private LoaiDonRepository_not_login loaiDonRepository;

    @Autowired
    private DiaChiRepository_not_login diaChiRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private LoaiHinhThucThanhToanRepository loaiHinhThucThanhToanRepository;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public MessageResponse thanhToanKhongDangNhap(CreateKhachRequest_not_login createKhachRequest_not_login) {
        List<TaiKhoan> khachHangList = khachHangRepository_not_login.getKhachHangByEmailOrSdt(createKhachRequest_not_login.getEmail(), createKhachRequest_not_login.getSoDienThoai());
        TaiKhoan khachHang;
        Optional<Voucher> voucher = voucherRepository.findById(createKhachRequest_not_login.getIdGiamGia());
        //Step1 : Xử lí khách hàng và địa chỉ
        if (!khachHangList.isEmpty()) {
            // Nếu tài khoản khách hàng đã tồn tại, sử dụng tài khoản đó.
            khachHang = khachHangList.get(0);
        } else {
            // Nếu không tìm thấy tài khoản, tạo tài khoản mới cho khách hàng.
            khachHang = new TaiKhoan();

            khachHang.setId(UUID.randomUUID());

            khachHang.setEmail(createKhachRequest_not_login.getEmail());

            khachHang.setName(createKhachRequest_not_login.getHoTen());

            khachHang.setSoDienThoai(createKhachRequest_not_login.getSoDienThoai());

            khachHangRepository_not_login.save(khachHang);

            //Tạo địa chỉ cho khách luôn

            DiaChi diaChi = new DiaChi();

            diaChi.setId(UUID.randomUUID());

            diaChi.setDiaChi(createKhachRequest_not_login.getDiaChi());

            diaChi.setQuocGia("VietNam");

            diaChi.setTaiKhoan(khachHang);

            diaChiRepository.save(diaChi);

        }//End step 1

        //Step2 : Xử lí hóa đơn

        // Tạo hóa đơn mới và gán thông tin tài khoản khách hàng.
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);

        HoaDon hoaDon = new HoaDon();

        hoaDon.setId(UUID.randomUUID());

        hoaDon.setMa(maHd);

        hoaDon.setNgayTao(timestamp);

        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setDiaChi(createKhachRequest_not_login.getDiaChi());
        hoaDon.setSdtNguoiNhan(createKhachRequest_not_login.getSoDienThoai());
        hoaDon.setTenNguoiNhan(createKhachRequest_not_login.getHoTen());
        hoaDon.setTaiKhoanKhachHang(khachHang);
        hoaDon.setEmail(createKhachRequest_not_login.getEmail());


        hoaDon.setLoaiDon(loaiDonRepository.findByTrangThai(TypeOrderEnums.ONLINE.getValue()).get());

        hoaDon.setThanhTien(createKhachRequest_not_login.getTongTien());

        hoaDon.setTienKhachTra(createKhachRequest_not_login.getTienKhachTra());

//        hoaDon.setTienGiamGia(BigDecimal.ZERO);

        hoaDon.setTienGiamGia(createKhachRequest_not_login.getTienGiamGia());

        //Nếu null sẽ không lưu
        if(voucher.isPresent()){
            hoaDon.setVoucher(voucher.get());
            // Tăng giá trị soluongdung lên 1
            voucher.get().setSoLuongDung(voucher.get().getSoLuongDung() + 1);
            // Lưu lại vào cơ sở dữ liệu
            voucherRepository.save(voucher.get());
        }

        hoaDonRepository.save(hoaDon);
        //End step 2

        TrangThaiHoaDon tthd = new TrangThaiHoaDon();
        tthd.setId(UUID.randomUUID());
        tthd.setGhiChu("Người mua tạo đơn hàng");
        tthd.setTrangThai(1);
        tthd.setThoiGian(timestamp);
        tthd.setHoaDon(hoaDon);

        trangThaiHoaDonRepository.save(tthd);

        //Step3 : Xử lí hóa đơn chi tiết
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
            // Tạo chi tiết hóa đơn cho mỗi sản phẩm trong giỏ hàng.

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                hoaDonChiTiet.setId(UUID.randomUUID());

                hoaDonChiTiet.setHoaDon(hoaDon);

                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());

                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());

                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));

                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());

                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

            } else {
                System.out.println("giỏ hàng chi tiết không tồn tại !");
                return MessageResponse.builder().message("Thanh Toán thất bại").build();
            }
        }//End step 3

//        //Step 4 : Xử lí hóa đơn chi tiết cập nhật số lượng trong kho
//        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
//            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
//
//            if (gioHangChiTiet.isPresent()) {
//                // Giảm số lượng sản phẩm trong kho đi số lượng đã bán trong chi tiết hóa đơn
//                gioHangChiTiet.get().getSanPhamChiTiet().setSoLuong(gioHangChiTiet.get().getSanPhamChiTiet().getSoLuong() - gioHangChiTiet.get().getSoLuong());
//                chiTietSanPhamRepository.save(gioHangChiTiet.get().getSanPhamChiTiet());
//            }
//        }//End step 4

        //Step 5 : Cập nhật trạng thái của giỏ hàng thành 2 sau khi thanh toán
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                System.out.println(gioHangChiTiet.get().getGioHang().getTrangThai());
                gioHangChiTiet.get().getGioHang().setTrangThai(2);
                gioHangRepository_not_login.save(gioHangChiTiet.get().getGioHang());
            }

        }//End step 5

        //Step 6 : Đặt số lượng tất cả các sản phẩm trong giỏ hàng về 0
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                gioHangChiTiet.get().setSoLuong(0);
                gioHangChiTietRepository.save(gioHangChiTiet.get());
            }

        }//End step 6
//        SendEmailOrder.sendEmailOrder(hoaDon, javaMailSender);
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();

    }

    @Override
    public MessageResponse yeuCauTraHang(UUID idHoaDon, UUID idHoaDonChiTiet, String lyDo) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        HoaDon hoaDon = hoaDonRepository.findById(idHoaDon).get();
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(idHoaDonChiTiet).get();
        hoaDonChiTiet.setTrangThai(9);
        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(9);
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDon.setGhiChu(lyDo);
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        return MessageResponse.builder().message("Thành công").build();
    }

    public UUID thanhToanLogin(CreateKhachRequest_not_login createKhachRequest_not_login, Principal principal) {

        Optional<Voucher> voucher = voucherRepository.findById(createKhachRequest_not_login.getIdGiamGia());

        //Step1 : Xử lí khách hàng và địa chỉ
        TaiKhoan khachHang = khachHangRepository_not_login.findByUsername(principal.getName());
        //End step 1

        //Step2 : Xử lí hóa đơn

        // Tạo hóa đơn mới và gán thông tin tài khoản khách hàng.
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);

        HoaDon hoaDon = new HoaDon();

        hoaDon.setId(UUID.randomUUID());

        hoaDon.setMa(maHd);

        hoaDon.setNgayTao(timestamp);

        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setDiaChi(createKhachRequest_not_login.getDiaChi());
        hoaDon.setSdtNguoiNhan(createKhachRequest_not_login.getSoDienThoai());
        hoaDon.setTenNguoiNhan(createKhachRequest_not_login.getHoTen());
        hoaDon.setTaiKhoanKhachHang(khachHang);
        hoaDon.setEmail(createKhachRequest_not_login.getEmail());

        hoaDon.setLoaiDon(loaiDonRepository.findByTrangThai(TypeOrderEnums.ONLINE.getValue()).get());

        hoaDon.setThanhTien(createKhachRequest_not_login.getTongTien());

        hoaDon.setTienKhachTra(createKhachRequest_not_login.getTienKhachTra());

        hoaDon.setTienGiamGia(createKhachRequest_not_login.getTienGiamGia());

        //Nếu null sẽ không lưu
        if(voucher.isPresent()){
            hoaDon.setVoucher(voucher.get());
            // Tăng giá trị soluongdung lên 1
            voucher.get().setSoLuongDung(voucher.get().getSoLuongDung() + 1);
            // Lưu lại vào cơ sở dữ liệu
            voucherRepository.save(voucher.get());
        }

        hoaDonRepository.save(hoaDon);
        //End step 2

        TrangThaiHoaDon tthd = new TrangThaiHoaDon();
        tthd.setId(UUID.randomUUID());
        tthd.setGhiChu("Người mua tạo đơn hàng");
        tthd.setTrangThai(1);
        tthd.setThoiGian(timestamp);
        tthd.setHoaDon(hoaDon);

        trangThaiHoaDonRepository.save(tthd);

        //Step3 : Xử lí hóa đơn chi tiết
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
            // Tạo chi tiết hóa đơn cho mỗi sản phẩm trong giỏ hàng.

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {

                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();

                hoaDonChiTiet.setId(UUID.randomUUID());

                hoaDonChiTiet.setHoaDon(hoaDon);

                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());

                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());

                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));

                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());

                hoaDonChiTiet.setTrangThai(1);

                hoaDonChiTietRepository.save(hoaDonChiTiet);

            } else {
                System.out.println("giỏ hàng chi tiết không tồn tại !");
                return null;
            }
        }//End step 3

//        //Step 4 : Xử lí hóa đơn chi tiết cập nhật số lượng trong kho
//        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {
//            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
//
//            if (gioHangChiTiet.isPresent()) {
//                // Giảm số lượng sản phẩm trong kho đi số lượng đã bán trong chi tiết hóa đơn
//                gioHangChiTiet.get().getSanPhamChiTiet().setSoLuong(gioHangChiTiet.get().getSanPhamChiTiet().getSoLuong() - gioHangChiTiet.get().getSoLuong());
//                chiTietSanPhamRepository.save(gioHangChiTiet.get().getSanPhamChiTiet());
//            }
//        }//End step 4

        //Step 5 : Cập nhật trạng thái của giỏ hàng thành 2 sau khi thanh toán
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                System.out.println(gioHangChiTiet.get().getGioHang().getTrangThai());
                gioHangChiTiet.get().getGioHang().setTrangThai(2);
                gioHangRepository_not_login.save(gioHangChiTiet.get().getGioHang());
            }

        }//End step 5

        //Step 6 : Đặt số lượng tất cả các sản phẩm trong giỏ hàng về 0
        for (UUID idGioHangChiTiet : createKhachRequest_not_login.getGioHangChiTietList()) {

            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);

            if (gioHangChiTiet.isPresent()) {
                gioHangChiTiet.get().setSoLuong(0);
                gioHangChiTietRepository.save(gioHangChiTiet.get());
            }

        }//End step 6
//        SendEmailOrder.sendEmailOrder(hoaDon, javaMailSender);
        return hoaDon.getId();
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

    public MessageResponse cashVnPay(UUID id,BigDecimal vnpAmount, String maGiaoDinh) {
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        LoaiHinhThucThanhToan loaiHinhThucThanhToan = new LoaiHinhThucThanhToan();
        loaiHinhThucThanhToan.setId(UUID.randomUUID());
        loaiHinhThucThanhToan.setNgayTao(new Date(System.currentTimeMillis()));
        loaiHinhThucThanhToan.setTenLoai("Khách thanh toán");
        loaiHinhThucThanhToanRepository.save(loaiHinhThucThanhToan);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTongSoTien( vnpAmount);
        hinhThucThanhToan.setPhuongThucThanhToan(2);
        hinhThucThanhToan.setCodeTransaction(maGiaoDinh);
        hinhThucThanhToan.setHoaDon(hoaDon);
        hinhThucThanhToan.setTrangThai(2);
        hinhThucThanhToan.setLoaiHinhThucThanhToan(loaiHinhThucThanhToan);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }
}