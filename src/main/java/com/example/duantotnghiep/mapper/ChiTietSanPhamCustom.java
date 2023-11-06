package com.example.duantotnghiep.mapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ChiTietSanPhamCustom {

    private String imgage;

    private UUID id;

    private String tenSanPham;

    private BigDecimal giaBan;

    private BigDecimal giaGiamGia;

    private Long mucGiam;

    private Integer soLuong;

    private String kieuDe;

    private String mauSac;

    private Integer size;

    private String chatLieu;
}
