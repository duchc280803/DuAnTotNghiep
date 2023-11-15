package com.example.duantotnghiep.service.account_service.impl;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.repository.TaiKhoanRepository;
import com.example.duantotnghiep.request.NhanVienDTORequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.NhanVienDTOReponse;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class NhanVienServiceImpl implements NhanVienCustomService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;

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
    public MessageResponse create(NhanVienDTORequest request) {
        return create(request, false);
    }

    @Override
    public MessageResponse create(NhanVienDTORequest request, boolean sendEmail) {
        TaiKhoan taiKhoan = new TaiKhoan();
        Optional<LoaiTaiKhoan> loaiTaiKhoanOptional = loaiTaiKhoanRepository.findById(request.getIdLoaiTaiKhoan());
        if (loaiTaiKhoanOptional.isPresent()) {
            taiKhoan.setId(UUID.randomUUID());
            taiKhoan.setNgaySinh(request.getNgaySinh());
            taiKhoan.setSoDienThoai(request.getSoDienThoai());
            taiKhoan.setGioiTinh(request.getGioiTinh());
            taiKhoan.setEmail(request.getEmail());
            taiKhoan.setUsername(request.getUsername());
            taiKhoan.setMatKhau(passwordEncoder.encode(request.getPassword()));
            taiKhoan.setLoaiTaiKhoan(loaiTaiKhoanOptional.get());
            taiKhoan.setTrangThai(request.getTrangThai());
            taiKhoan.setImage("default.png");
            taiKhoan.setName(request.getFullName());
            taiKhoan.setMaTaiKhoan(request.getMaTaiKhoan());
            taiKhoanRepository.save(taiKhoan);

            if (sendEmail) {
                sendConfirmationEmail(taiKhoan.getEmail(), taiKhoan.getUsername(), request.getPassword());
                System.out.println("gửi mail");
            }

            return MessageResponse.builder().message("Thêm thành công").build();
        }
        return MessageResponse.builder().message("Thêm thất bại").build();
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
            Optional<LoaiTaiKhoan> loaiTaiKhoanOptional = loaiTaiKhoanRepository.findById(request.getIdLoaiTaiKhoan());
            if (loaiTaiKhoanOptional.isPresent()) {
                TaiKhoan taiKhoan = taiKhoanOptional.get();
                taiKhoan.setNgaySinh(request.getNgaySinh());
                taiKhoan.setSoDienThoai(request.getSoDienThoai());
                taiKhoan.setGioiTinh(request.getGioiTinh());
                taiKhoan.setEmail(request.getEmail());
                taiKhoan.setTrangThai(request.getTrangThai());
                taiKhoan.setImage("default.png");
                taiKhoan.setName(request.getFullName());
                taiKhoan.setMaTaiKhoan(request.getMaTaiKhoan());
                taiKhoanRepository.save(taiKhoan);
                return MessageResponse.builder().message("Cập nhật thành công").build();
            }
            return MessageResponse.builder().message("Cập nhật thất bại không được vì thiếu loại tài khoản").build();
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


}
