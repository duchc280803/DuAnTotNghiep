package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamHoaDonChiTietResponse {

    private UUID idHoaDonChiTiet;

    private String tenImage;

    private String tenSanPham;

    private BigDecimal giaBan;

    private BigDecimal donGiaSauGiam;

    private Integer soLuong;

}
