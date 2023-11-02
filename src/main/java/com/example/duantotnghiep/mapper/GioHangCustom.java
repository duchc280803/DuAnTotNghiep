package com.example.duantotnghiep.mapper;

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
public class GioHangCustom {

    UUID idGioHang;
    String image;
    String tenSanPham;
    BigDecimal giaBan;
    BigDecimal giaGiam;
    Long mucGiam;
    Integer soLuong;
    Integer size;
    String kieuDe;
    String mauSac;
}
