package com.example.duantotnghiep.mapper;

import com.example.duantotnghiep.dto.NhanVienDTO;
import com.example.duantotnghiep.entity.TaiKhoan;

import java.util.UUID;

public class NhanVienMapper {

    public static NhanVienDTO nhanVienDTO(TaiKhoan taiKhoan) {
        return new NhanVienDTO(
                taiKhoan.getId().randomUUID(),
                taiKhoan.getUserName(),
                taiKhoan.getMatKhau(),
                taiKhoan.getEmail()
        );
    }

    public static TaiKhoan taiKhoan(NhanVienDTO nhanVienDTO) {
        return new TaiKhoan(
                nhanVienDTO.getId().randomUUID(),
                nhanVienDTO.getUsername(),
                nhanVienDTO.getPassword(),
                nhanVienDTO.getEmail()
        );
    }

}
