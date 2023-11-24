package com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.impl;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.request.CreateQLKhachHangRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import com.example.duantotnghiep.service.quan_ly_nhan_vien_khach_hang.QLKhachHangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Override
    public List<QLKhachHangResponse> getQLKhachHang(Integer trangThai, String name, String soDienThoai, String maTaiKhoan, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QLKhachHangResponse> pageList = khachHangRepository.findlistQLKhachHang(trangThai, name, soDienThoai, maTaiKhoan, pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse createKhachHang(MultipartFile file, CreateQLKhachHangRequest createQLKhachHangRequest, boolean sendEmail) {
        String fileName = file.getOriginalFilename();
        List<TaiKhoan> taiKhoans = khachHangRepository.listNhanVien();
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\FE_DuAnTotNghiep\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String normalized = removeDiacritics(createQLKhachHangRequest.getTen());

        // Chuyển đổi chuỗi thành chữ thường và loại bỏ khoảng trắng
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
        if (sendEmail) {
            sendConfirmationEmail(taiKhoan.getEmail(), taiKhoan.getUsername(), converted);
            System.out.println("gửi mail");
        }
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    private void sendConfirmationEmail(String email, String username, String password) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Chào mừng bạn đến với Nice Shoe");
        simpleMailMessage.setText("Tài khoản của bạn đã được tạo thành công. \n\n" +
                "Thông tin đăng nhập:\n" +
                "Username: " + username + "\n" +
                "Password: " + password);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public QLKhachHangResponse detailKhachHang(UUID id) {
        return khachHangRepository.detailQLKhachHang(id);
    }

    @Override
    public MessageResponse updateKhachHang(UUID khachHangId, CreateQLKhachHangRequest createQLKhachHangRequest) {
        Optional<TaiKhoan> optionalTaiKhoan = khachHangRepository.findById(khachHangId);

        if (optionalTaiKhoan.isPresent()) {
            TaiKhoan taiKhoan = optionalTaiKhoan.get();

            taiKhoan.setName(createQLKhachHangRequest.getTen());
            taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
            taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
            taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
            taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
            taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
            taiKhoan.setMaTaiKhoan(createQLKhachHangRequest.getMaTaiKhoan());

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
