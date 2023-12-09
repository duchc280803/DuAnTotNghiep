package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TrangThaiHoaDonRequest {

    private Integer newTrangThai;

    @NotBlank(message = "Không được để trống ghi chú")
    private String ghiChu;
}
