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

    private UUID idSanPhamChiTiet;

    private String imgage;

    private String tenSanPham;

    private String giaBan;

    private String giaGiamGia;

    private Integer soLuong;

    private String mauSac;

    private Integer size;

    private String chatLieu;
}
