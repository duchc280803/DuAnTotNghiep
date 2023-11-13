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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QLKhachHangImpl implements QLKhachHangService {
    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Override
    public List<QLKhachHangResponse> getQLKhachHang(Integer trangThai, String name, String soDienThoai, String maTaiKhoan, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<QLKhachHangResponse> pageList = khachHangRepository.findlistQLKhachHang(trangThai, name, soDienThoai, maTaiKhoan, pageable);
        return pageList.getContent();
    }

    @Override
    public MessageResponse createKhachHang(CreateQLKhachHangRequest createQLKhachHangRequest) {
        LoaiTaiKhoan loaiTaiKhoan = loaiTaiKhoanRepository.findByName(TypeAccountEnum.USER).get();
        TaiKhoan taiKhoan = new TaiKhoan();
        taiKhoan.setId(UUID.randomUUID());
        taiKhoan.setName(createQLKhachHangRequest.getTen());
        taiKhoan.setEmail(createQLKhachHangRequest.getEmail());
        taiKhoan.setSoDienThoai(createQLKhachHangRequest.getSoDienThoai());
        taiKhoan.setImage(createQLKhachHangRequest.getImage());
        taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
        taiKhoan.setUsername(createQLKhachHangRequest.getUserName());
        taiKhoan.setMatKhau(createQLKhachHangRequest.getMatKhau());
        taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
        taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
        taiKhoan.setMaTaiKhoan(createQLKhachHangRequest.getMaTaiKhoan());
        taiKhoan.setLoaiTaiKhoan(loaiTaiKhoan);
        khachHangRepository.save(taiKhoan);
        return MessageResponse.builder().message("Thêm Thành Công").build();
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
            taiKhoan.setImage(createQLKhachHangRequest.getImage());
            taiKhoan.setGioiTinh(createQLKhachHangRequest.getGioiTinh());
            taiKhoan.setUsername(createQLKhachHangRequest.getUserName());
            taiKhoan.setNgaySinh(createQLKhachHangRequest.getNgaySinh());
            taiKhoan.setTrangThai(createQLKhachHangRequest.getTrangThai());
            taiKhoan.setMaTaiKhoan(createQLKhachHangRequest.getMaTaiKhoan());

            khachHangRepository.save(taiKhoan);
            return MessageResponse.builder().message("Cập Nhật Thành Công").build();
        } else {
            return MessageResponse.builder().message("Không Tìm Thấy Khách Hàng").build();
        }
    }




}
