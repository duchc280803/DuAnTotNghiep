package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class SanPhamGiamGiaResponse {

    private UUID idSanPham;

    private UUID idSpCt;

    private String image;

    private String tenSp;

    private BigDecimal giaBan;

    private BigDecimal giaGiamGia;

    private Long mucGiam;

    public SanPhamGiamGiaResponse(UUID idSanPham, UUID idSpCt, String image, String tenSp, BigDecimal giaBan, BigDecimal giaGiamGia, Long mucGiam) {
        this.idSanPham = idSanPham;
        this.idSpCt = idSpCt;
        this.image = image;
        this.tenSp = tenSp;
        this.giaBan = giaBan;
        this.giaGiamGia = giaGiamGia;
        this.mucGiam = mucGiam;
    }

}
