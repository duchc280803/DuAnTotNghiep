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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final PasswordEncoder passwordEncoder;

    @Override
    public List<NhanVienDTOReponse> getAllNhanVien(String maTaiKhoan, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<NhanVienDTOReponse> pageList = taiKhoanRepository.getAllNhanVien(maTaiKhoan, trangThai, pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse create(NhanVienDTORequest request) {
        TaiKhoan taiKhoan = new TaiKhoan();
        Optional<LoaiTaiKhoan> loaiTaiKhoanOptional = loaiTaiKhoanRepository.findByName(request.getTenLoaiTaiKhoan());
        if (loaiTaiKhoanOptional.isPresent()) {
            System.out.println("Tên loại tài khoản: " +loaiTaiKhoanOptional.get().toString());
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
            taiKhoan.setMaTaiKhoan(request.getMaTaiKhoan());
            taiKhoanRepository.save(taiKhoan);
            return MessageResponse.builder().message("Thêm thành công").build();
        }
        return MessageResponse.builder().message("Thêm thất bại").build();

    }

    @Override
    public MessageResponse update(UUID id, NhanVienDTORequest request) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if (taiKhoanOptional.isPresent()) {
            Optional<LoaiTaiKhoan> loaiTaiKhoanOptional = loaiTaiKhoanRepository.findByName(request.getTenLoaiTaiKhoan());
            if (loaiTaiKhoanOptional.isPresent()) {
                TaiKhoan taiKhoan = taiKhoanOptional.get();
                taiKhoan.setNgaySinh(request.getNgaySinh());
                taiKhoan.setSoDienThoai(request.getSoDienThoai());
                taiKhoan.setGioiTinh(request.getGioiTinh());
                taiKhoan.setEmail(request.getEmail());
                taiKhoan.setUsername(request.getUsername());
                taiKhoan.setMatKhau(passwordEncoder.encode(request.getPassword()));
                taiKhoan.setLoaiTaiKhoan(loaiTaiKhoanOptional.get());
                taiKhoan.setTrangThai(request.getTrangThai());
                taiKhoan.setImage("default.png");
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
