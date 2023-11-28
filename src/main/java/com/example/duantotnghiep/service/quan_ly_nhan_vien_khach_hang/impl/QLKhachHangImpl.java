package com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.DiaChiRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.QLKhachHangService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class QLKhachHangImpl implements QLKhachHangService {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DiaChiRepository diaChiRepository;

    @Override
    public List<QLKhachHangResponse> getQLKhachHang(Integer trangThai, String name, String soDienThoai, String maTaiKhoan, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QLKhachHangResponse> pageList = khachHangRepository.findlistQLKhachHang(trangThai, name, soDienThoai, maTaiKhoan, pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse createKhachHang(MultipartFile file, CreateQLKhachHangRequest createQLKhachHangRequest, boolean sendEmail) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        List<TaiKhoan> taiKhoans = khachHangRepository.listKhachHang();
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\FE_DuAnTotNghiep\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String normalized = removeDiacritics(createQLKhachHangRequest.getTen());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");

        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(createQLKhachHangRequest.getTen());
        taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
        taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
        taiKhoan.setImage(fileName);
        taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
        taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
        taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        khachHangRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(createQLKhachHangRequest.getDiaChi());
        diaChi.setXa(createQLKhachHangRequest.getPhuong());
        diaChi.setHuyen(createQLKhachHangRequest.getHuyen());
        diaChi.setTinh(createQLKhachHangRequest.getTinh());
        diaChi.setTaiKhoan(taiKhoan);
        diaChiRepository.save(diaChi);
        if (sendEmail) {
            sendConfirmationEmail(taiKhoan.getEmail(), taiKhoan.getUsername(), converted);
            System.out.println("gửi mail");
        }
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    private void sendConfirmationEmail(String email, String username, String password) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        try {
            helper.setTo(email);
            helper.setSubject("Chào mừng bạn đến với Nice Shoe");

            String htmlMsg = "<h1>Chào mừng bạn đến với <span style='color: #ff9900;'>NICE SHOE</span> của chúng tôi!</h1>\n" +
                    "<p>Xin chân thành cảm ơn bạn đã đăng ký nhận <span style='color: #ff9900;'>NICE SHOE</span> của chúng tôi. Chúng tôi sẽ cung cấp cho bạn thông tin cập\n" +
                    "    nhật về tin tức và ưu đãi mới nhất.</p>\n" +
                    "<h3>Ưu điểm của <span style='color: #ff9900;'>NICE SHOE</span>:</h3>\n" +
                    "<ul>\n" +
                    "    <li>Thông tin mới nhất về sản phẩm và dịch vụ của chúng tôi</li>\n" +
                    "    <li>Ưu đãi đặc biệt và khuyến mãi hấp dẫn</li>\n" +
                    "</ul>\n" +
                    "<p><strong>Đừng bỏ lỡ!</strong> Để nhận các thông tin và ưu đãi đặc biệt từ chúng tôi, hãy nhấp vào nút bên dưới để\n" +
                    "    mua ngay sản phẩm:</p>\n" +
                    "<a href='LINK_DEN_TRANG_DANG_KY'\n" +
                    "    style='padding: 10px 20px; background-color: #ff9900; color: #ffffff; text-decoration: none; border-radius: 5px;'>Trang web</a>" +
                    "<p><strong>Thông tin đăng nhập:</strong></p>" +
                    "<p>Username: <strong>" + username + "</strong></p>" +
                    "<p>Password: <strong>" + password + "</strong></p>";

            helper.setText(htmlMsg, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Xử lý ngoại lệ nếu gửi email thất bại
            e.printStackTrace();
        }
    }

    @Override
    public QLKhachHangResponse detailKhachHang(UUID id) {
        return khachHangRepository.detailQLKhachHang(id);
    }

    @Override
    public MessageResponse updateKhachHang(MultipartFile file, UUID khachHangId, CreateQLKhachHangRequest createQLKhachHangRequest) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Optional<TaiKhoan> optionalTaiKhoan = khachHangRepository.findById(khachHangId);
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\FE_DuAnTotNghiep\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();

            taiKhoan.setName(createQLKhachHangRequest.getTen());
            taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
            taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
            taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
            taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
            taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
            taiKhoan.setImage(fileName);

            khachHangRepository.save(taiKhoan);
            return MessageResponse.builder().message("Cập Nhật Thành Công").build();
        } else {
            return MessageResponse.builder().message("Không Tìm Thấy Khách Hàng").build();
        }
    }

    public static String removeDiacritics(String input) {
        input = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(input).replaceAll("");
    }
}
