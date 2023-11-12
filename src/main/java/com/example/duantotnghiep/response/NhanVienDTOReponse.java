package com.example.duantotnghiep.response;

import com.example.duantotnghiep.enums.TypeAccountEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NhanVienDTOReponse {
    private UUID id;

    private String fullName;

    private String maTaiKhoan;

    private String soDienThoai;

    private Boolean gioiTinh;

    private TypeAccountEnum loaiTaiKhoan;

    private Integer trangThai;

    private String image;

}
