package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.enums.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.OrderCounterCartsResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.ban_tai_quay_service.OrderCounterService;
import com.example.duantotnghiep.util.FormatNumber;
import com.example.duantotnghiep.util.RemoveDiacritics;
import com.example.duantotnghiep.util.SendConfirmationEmail;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class OrderCounterServiceImpl implements OrderCounterService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private LoaiDonRepository loaiDonRepository;

    @Autowired
    private TrangThaiHoaDonRepository trangThaiHoaDonRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    // TODO Thêm hóa đơn tại quầy
    public HoaDon taoHoaDon(String name) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(name);

        Optional<LoaiDon> findByLoaiDon = loaiDonRepository.findByTrangThai(TypeOrderEnums.TAI_QUAY.getValue());

        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName("Khách lẻ");
        taiKhoan.setTrangThai(1);
        taiKhoanRepository.save(taiKhoan);

        Random rand = new Random();
        int randomNumber = rand.nextInt(100000);
        String maHd = String.format("HD%03d", randomNumber);
        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
        hoaDon.setTenNguoiNhan("Khách lẻ");
        hoaDon.setNgayTao(timestamp);
        hoaDon.setTienGiamGia(BigDecimal.ZERO);
        hoaDon.setTrangThai(StatusOrderEnums.CHO_XAC_NHAN.getValue());
        hoaDon.setTaiKhoanNhanVien(findByNhanVien.get());
        hoaDon.setTaiKhoanKhachHang(taiKhoan);
        hoaDon.setLoaiDon(findByLoaiDon.get());
        hoaDonRepository.save(hoaDon);

        GioHang gioHang = new GioHang();
        gioHang.setId(UUID.randomUUID());
        gioHang.setTaiKhoan(taiKhoan);
        gioHang.setNgayTao(timestamp);
        gioHang.setTrangThai(StatusCartEnums.CHUA_CO_SAN_PHAM.getValue());
        gioHangRepository.save(gioHang);

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.CHO_XAC_NHAN.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setGhiChu("Nhân viên tạo đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon);
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        auditLogService.writeAuditLogHoadon(name, findByNhanVien.get().getEmail(), "Nhân viên tạo hóa đơn", hoaDon.getMa(),  "" , "",  "", "");
        return hoaDon;
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<HoaDonResponse> hoaDonResponses = hoaDonRepository.viewHoaDonTaiQuay(pageable);
        return hoaDonResponses.getContent();
    }

    @Override
    public List<HoaDonResponse> findByCodeOrder(String ma) {
        return hoaDonRepository.findByCodeOrder(ma);
    }

    public Long getGiaGiamCuoiCung(UUID id) {
        long tongTienGiam = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            // Cập nhật tổng tiền giảm đúng cách, không khai báo lại biến tongTienGiam
            if (spGiamGia.getGiaGiam() == null) {
                return tongTienGiam;
            }
            if (spGiamGia.getGiaGiam() != null){
                tongTienGiam += spGiamGia.getGiaGiam().longValue();
            }

        }

        return tongTienGiam;
    }

    @Override
    public MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        hoaDon.get().setNgayNhan(timestamp);
        hoaDon.get().setNgayCapNhap(timestamp);
        hoaDon.get().setTienKhachTra(hoaDonThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonThanhToanRequest.getTongTien());
        hoaDon.get().setTenNguoiNhan(hoaDonThanhToanRequest.getHoTen());
        hoaDon.get().setSdtNguoiNhan(hoaDonThanhToanRequest.getSoDienThoai());
        hoaDon.get().setDiaChi(hoaDonThanhToanRequest.getDiaChi());
        hoaDon.get().setTrangThai(5);
        hoaDonRepository.save(hoaDon.get());

        for (UUID idGioHangChiTiet : hoaDonThanhToanRequest.getGioHangChiTietList()) {
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
                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(5);
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.get().getSanPhamChiTiet().getId()).get();
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.get().getSoLuong());
                chiTietSanPhamRepository.save(sanPhamChiTiet);
            }
        }

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.HOAN_THANH.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên xác nhận đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);

        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }

    @Override
    public MessageResponse updateHoaDonGiaoTaiQuay(UUID idHoaDon, HoaDonGiaoThanhToanRequest hoaDonGiaoThanhToanRequest, String username, boolean sendEmail) throws IOException, CsvValidationException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        Optional<TaiKhoan> findByNhanVien = taiKhoanRepository.findByUsername(username);

        hoaDon.get().setNgayNhan(timestamp);
        hoaDon.get().setNgayCapNhap(timestamp);
        hoaDon.get().setTienKhachTra(hoaDonGiaoThanhToanRequest.getTienKhachTra());
        hoaDon.get().setTienThua(hoaDonGiaoThanhToanRequest.getTienThua());
        hoaDon.get().setThanhTien(hoaDonGiaoThanhToanRequest.getTongTien());
        hoaDon.get().setTenNguoiNhan(hoaDonGiaoThanhToanRequest.getHoTen());
        hoaDon.get().setSdtNguoiNhan(hoaDonGiaoThanhToanRequest.getSoDienThoai());
        hoaDon.get().setDiaChi(hoaDonGiaoThanhToanRequest.getDiaChi());
        hoaDon.get().setTienShip(hoaDonGiaoThanhToanRequest.getTienGiao());
        hoaDon.get().setEmail(hoaDonGiaoThanhToanRequest.getEmail());
        hoaDon.get().setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
        auditLogService.writeAuditLogHoadon(username, findByNhanVien.get().getEmail(), "Cập nhật địa chỉ", hoaDon.get().getMa(),
                "Tên người nhận: "+ hoaDonGiaoThanhToanRequest.getHoTen() ,
                "SĐT: "+hoaDonGiaoThanhToanRequest.getSoDienThoai(),
                "Địa chỉ: "+hoaDonGiaoThanhToanRequest.getDiaChi(), "Phí vận chuyển: "+hoaDonGiaoThanhToanRequest.getTienGiao());
        hoaDonRepository.save(hoaDon.get());

        for (UUID idGioHangChiTiet : hoaDonGiaoThanhToanRequest.getGioHangChiTietList()) {
            Optional<GioHangChiTiet> gioHangChiTiet = gioHangChiTietRepository.findById(idGioHangChiTiet);
            gioHangChiTiet.get().setTrangThai(StatusCartDetailEnums.DA_THANH_TOAN.getValue());
            gioHangChiTietRepository.save(gioHangChiTiet.get());
            Optional<GioHang> gioHang = gioHangRepository.findById(gioHangChiTiet.get().getGioHang().getId());
            gioHang.get().setNgayCapNhat(timestamp);
            gioHang.get().setTrangThai(StatusCartEnums.DA_THANH_TOAN.getValue());
            gioHangRepository.save(gioHang.get());
            if (gioHangChiTiet.isPresent()) {
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setId(UUID.randomUUID());
                hoaDonChiTiet.setHoaDon(hoaDon.get());
                hoaDonChiTiet.setSanPhamChiTiet(gioHangChiTiet.get().getSanPhamChiTiet());
                hoaDonChiTiet.setDonGia(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan());
                hoaDonChiTiet.setDonGiaSauGiam(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getGiaBan().subtract(new BigDecimal(getGiaGiamCuoiCung(gioHangChiTiet.get().getSanPhamChiTiet().getSanPham().getId()))));
                hoaDonChiTiet.setSoLuong(gioHangChiTiet.get().getSoLuong());
                hoaDonChiTiet.setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(gioHangChiTiet.get().getSanPhamChiTiet().getId()).get();
                System.out.println(gioHangChiTiet.get().getSanPhamChiTiet().getId());
                sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - gioHangChiTiet.get().getSoLuong());
                chiTietSanPhamRepository.save(sanPhamChiTiet);
            }
        }

        TrangThaiHoaDon trangThaiHoaDon = new TrangThaiHoaDon();
        trangThaiHoaDon.setId(UUID.randomUUID());
        trangThaiHoaDon.setTrangThai(StatusOrderDetailEnums.XAC_NHAN.getValue());
        trangThaiHoaDon.setThoiGian(timestamp);
        trangThaiHoaDon.setUsername(hoaDon.get().getTaiKhoanNhanVien().getMaTaiKhoan());
        trangThaiHoaDon.setGhiChu("Nhân viên xác nhận đơn cho khách");
        trangThaiHoaDon.setHoaDon(hoaDon.get());
        trangThaiHoaDonRepository.save(trangThaiHoaDon);
        if (sendEmail) {
            sendEmailOrder(hoaDon.get());
            System.out.println("gửi mail");
        }
        String normalized = RemoveDiacritics.removeDiacritics(hoaDonGiaoThanhToanRequest.getHoTen());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");
        List<TaiKhoan> taiKhoans = khachHangRepository.listKhachHang();
        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(hoaDonGiaoThanhToanRequest.getHoTen());
        taiKhoan.setEmail(hoaDonGiaoThanhToanRequest.getEmail());
        taiKhoan.setSoDienThoai(hoaDonGiaoThanhToanRequest.getSoDienThoai());
        taiKhoan.setTrangThai(1);
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        khachHangRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(hoaDonGiaoThanhToanRequest.getDiaChi());
        diaChi.setXa(hoaDonGiaoThanhToanRequest.getPhuong());
        diaChi.setHuyen(hoaDonGiaoThanhToanRequest.getHuyen());
        diaChi.setTinh(hoaDonGiaoThanhToanRequest.getTinh());
        diaChi.setTaiKhoan(taiKhoan);
        diaChi.setTrangThai(1);
        diaChiRepository.save(diaChi);
        if (sendEmail) {
            SendConfirmationEmail.sendConfirmationEmailStatic(taiKhoan.getEmail(), taiKhoan.getUsername(), converted, javaMailSender);
            System.out.println("gửi mail");
        }
        return MessageResponse.builder().message("Thanh Toán Thành Công").build();
    }

    public OrderCounterCartsResponse findByHoaDon(UUID id) {
        return hoaDonRepository.findByHoaDon(id);
    }

    @Override
    public IdGioHangResponse showIdGioHangCt(UUID id) {
        return hoaDonRepository.showIdGioHangCt(id);
    }

    @Override
    public void removeOrder(UUID id) {
        IdGioHangResponse idGioHangResponse = hoaDonRepository.showIdGioHangCt(id);
        GioHang gioHang = gioHangRepository.findById(idGioHangResponse.getId()).get();
        gioHang.setTrangThai(2);
        gioHangRepository.save(gioHang);
        HoaDon hoaDon = hoaDonRepository.findById(id).get();
        for (TrangThaiHoaDon x : hoaDon.getTrangThaiHoaDonList()) {
            if (x != null) {
                trangThaiHoaDonRepository.deleteById(x.getId());
            }
        }
        for (HinhThucThanhToan x : hoaDon.getHinhThucThanhToanList()) {
            if (x != null) {
                hinhThucThanhToanRepository.deleteById(x.getId());
            }
        }
        hoaDonRepository.deleteById(id);
    }

    private void sendEmailOrder(HoaDon hoaDon) {
        String productList = ""; // Chuỗi để lưu thông tin về sản phẩm

        List<HoaDonChiTiet> danhSachSanPham = hoaDon.getHoaDonChiTietList();// Giả sử danhSachSanPham lưu danh sách sản phẩm
        int stt = 1; // Biến để đánh số thứ tự

        for (HoaDonChiTiet sanPham : danhSachSanPham) {
            productList += "<tr>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + stt + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSanPhamChiTiet().getSanPham().getTenSanPham() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + sanPham.getSoLuong() + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam()) + "</td>\n" +
                    "    <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">" + FormatNumber.formatBigDecimal(sanPham.getDonGiaSauGiam().multiply(new BigDecimal(sanPham.getSoLuong()))) + "</td>\n" +
                    "</tr>\n";
            stt++;
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(hoaDon.getEmail());
            helper.setSubject("");

            String htmlMsg = "<body style=\"font-family: Arial, sans-serif;\">\n" +
                    "    <h1 style=\"color: #ff9900;\">Kính gửi Quý khách hàng: " + hoaDon.getTenNguoiNhan() + "</h1>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Chúng tôi xin chân thành cảm ơn Quý khách đã đặt hàng tại <strong  style=\"color: #ff9900;\">NICE SHOE</strong>.</h6>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về đơn hàng của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <tr>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Mã đơn hàng: " + hoaDon.getMa() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Ngày đặt hàng: " + hoaDon.getNgayTao() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Khách hàng: " + hoaDon.getTenNguoiNhan() + "</h6>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "        <tr>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Email: " + hoaDon.getEmail() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Số điện thoại: " + hoaDon.getSdtNguoiNhan() + "</h6>\n" +
                    "            </td>\n" +
                    "            <td style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">\n" +
                    "                <h6 style=\"color: black; font-size: 13px;\">Tình trạng: " + hoaDon.getTrangThai() + "</h6>\n" +
                    "            </td>\n" +
                    "        </tr>\n" +
                    "    </table>\n" +
                    "    <h6 style=\"color: black; font-size: 15px;\">Thông tin chi tiết về sản phẩm của Quý khách như sau:</h6>\n" +
                    "    <table style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n" +
                    "        <thead>\n" +
                    "            <tr>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">STT</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tên sản phẩm</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Số lượng</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Giá bán</th>\n" +
                    "                <th style=\"border: 1px solid #ddd; padding: 8px; text-align: left;\">Tổng cộng</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "    <tbody>\n" +
                    productList + // Thêm danh sách sản phẩm vào đây
                    "    </tbody>\n" +
                    "<tfoot>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền ship:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienShip()) + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tiền giảm giá:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getTienGiamGia()) + "</strong></td>\n" +
                    "    </tr>\n" +
                    "    <tr>\n" +
                    "        <td colspan=\"4\" style=\"text-align: right;\"><strong>Tổng tiền hàng:</strong></td>\n" +
                    "        <td><strong>" + FormatNumber.formatBigDecimal(hoaDon.getThanhTien()) + "</strong></td>\n" +
                    "    </tr>\n" +
                    "</tfoot>\n" +
                    "    </table>\n" +
                    "\n" +
                    "    <p>Chúng tôi sẽ xử lý đơn hàng của Quý khách sớm nhất có thể và thông báo cho Quý khách khi đơn hàng đã được giao\n" +
                    "        cho dịch vụ vận chuyển.</p>\n" +
                    "    <p>Nếu có bất kỳ thắc mắc hoặc yêu cầu nào, vui lòng liên hệ với chúng tôi qua thông tin sau:</p>\n" +
                    "    <p>- Điện thoại: 0971066455\n" +
                    "        - Email: niceshoepoly@gmail.com</p>\n" +
                    "    <p>Xin chân thành cảm ơn Quý khách hàng đã tin tưởng và lựa chọn sản phẩm của chúng tôi.</p>\n" +
                    "    <footer style=\"margin-top: 20px;\">Trân trọng, NICESHOE</footer>\n" +
                    "</body>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }

}
