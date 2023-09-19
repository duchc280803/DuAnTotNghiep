package com.example.duantotnghiep.mapper;

import com.example.duantotnghiep.request.NhanVienRequest;
import com.example.duantotnghiep.entity.TaiKhoan;

public class NhanVienMapper {

    public static NhanVienRequest nhanVienDTO(TaiKhoan taiKhoan) {
        return new NhanVienRequest(
                taiKhoan.getId().randomUUID(),
                taiKhoan.getUsername(),
                taiKhoan.getMatKhau(),
                taiKhoan.getEmail()
        );
    }

    public static TaiKhoan taiKhoan(NhanVienRequest nhanVienRequest) {
        return new TaiKhoan(
                nhanVienRequest.getId().randomUUID(),
                nhanVienRequest.getUsername(),
                nhanVienRequest.getFullName(),
                nhanVienRequest.getEmail()
        );
    }

}
