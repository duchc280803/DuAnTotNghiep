package com.example.duantotnghiep.request;

import com.example.duantotnghiep.enums.TypeAccountEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienDTORequest {
    private String fullName;

    private String maTaiKhoan;

    private Date ngaySinh;

    private String soDienThoai;

    private Boolean gioiTinh;

    private String email;

    private String username;

    private String password;

    private TypeAccountEnum tenLoaiTaiKhoan;

    private Integer trangThai;

    private String image;


}
