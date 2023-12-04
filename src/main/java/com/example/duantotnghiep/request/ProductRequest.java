package com.example.duantotnghiep.request;

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
public class ProductRequest {

    private String maSanPham;

    private String productName;

    private String describe;

    private BigDecimal price;

    private Integer baoHang;

    private UUID idKieuDe;

    private UUID idXuatXu;

    private UUID idBrand;

    private UUID idCategory;
}
