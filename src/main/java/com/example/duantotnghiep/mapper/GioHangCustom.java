package com.example.duantotnghiep.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GioHangCustom {
    String image;
    String tenSanPham;
    BigDecimal giaBan;
    Integer soLuong;
    Integer size;
    String kieuDe;
    String mauSac;
}
