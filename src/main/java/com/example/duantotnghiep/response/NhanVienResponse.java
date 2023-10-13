package com.example.duantotnghiep.response;

import com.example.duantotnghiep.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NhanVienResponse {

    private UUID id;

    private String fullName;

    private String email;

    private String soDienThoai;
}
