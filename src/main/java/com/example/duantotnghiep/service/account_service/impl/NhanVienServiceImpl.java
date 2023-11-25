package com.example.duantotnghiep.service.account_service.impl;

import com.example.duantotnghiep.entity.DiaChi;
import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.DiaChiRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
import com.example.duantotnghiep.response.NhanVienOrderResponse;
import com.example.duantotnghiep.service.account_service.NhanVienCustomService;
import lombok.RequiredArgsConstructor;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienCustomService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

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
    public List<NhanVienDTOReponse> getAllNhanVien(String maTaiKhoan, String name, String soDienThoai, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Integer> trangThaiList = Arrays.asList(1, 2, 5);
        Page<NhanVienDTOReponse> pageList = taiKhoanRepository.getAllNhanVien(trangThaiList, maTaiKhoan, name, soDienThoai, trangThai, pageable);
        return pageList.getContent();
    }

    @Override
    public TaiKhoan getById(UUID id) {
        return taiKhoanRepository.findById(id).orElse(null);
    }

    @Override
    public NhanVienDTOReponse getNhanVienById(UUID id) {
        return taiKhoanRepository.getNhanVienById(id);
    }

    @Override
    public MessageResponse create(MultipartFile file, NhanVienDTORequest request, boolean sendEmail) {
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        List<NhanVienOrderResponse> taiKhoans = khachHangRepository.listNv();
        try {
            Files.copy(file.getInputStream(), Paths.get("D:\\FE_DuAnTotNghiep\\assets\\ảnh giày", fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String normalized = removeDiacritics(request.getTen());

        String converted = normalized.toLowerCase().replaceAll("\\s", "");

        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.STAFF).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(request.getTen());
        taiKhoan.setEmail(request.getEmail());
        taiKhoan.setSoDienThoai(request.getSoDienThoai());
        taiKhoan.setImage(fileName);
        taiKhoan.setGioiTinh(request.getGioiTinh());
        taiKhoan.setNgaySinh(request.getNgaySinh());
        taiKhoan.setTrangThai(request.getTrangThai());
        taiKhoan.setMaTaiKhoan(converted + taiKhoans.size() + 1);
        taiKhoan.setUsername(converted + taiKhoans.size() + 1);
        taiKhoan.setMatKhau(passwordEncoder.encode(converted));
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        taiKhoanRepository.save(taiKhoan);

        DiaChi diaChi = new DiaChi();
        diaChi.setId(UUID.randomUUID());
        diaChi.setDiaChi(request.getDiaChi());
        diaChi.setXa(request.getPhuong());
        diaChi.setHuyen(request.getHuyen());
        diaChi.setTinh(request.getTinh());
        diaChi.setTaiKhoan(taiKhoan);
        diaChi.setTrangThai(1);
        diaChiRepository.save(diaChi);
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
    public MessageResponse update(UUID id, NhanVienDTORequest request) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if (taiKhoanOptional.isPresent()) {
                TaiKhoan taiKhoan = taiKhoanOptional.get();
                taiKhoan.setNgaySinh(request.getNgaySinh());
                taiKhoan.setSoDienThoai(request.getSoDienThoai());
                taiKhoan.setGioiTinh(request.getGioiTinh());
                taiKhoan.setEmail(request.getEmail());
                taiKhoan.setTrangThai(request.getTrangThai());
                taiKhoan.setImage("default.png");
                taiKhoan.setName(request.getTen());
                taiKhoanRepository.save(taiKhoan);
                return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy tài khoản với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if (taiKhoanOptional.isPresent()) {
            taiKhoanOptional.get().setTrangThai(2);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    public static String removeDiacritics(String input) {
        input = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(input).replaceAll("");
    }

}
