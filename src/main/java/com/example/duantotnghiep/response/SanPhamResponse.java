package com.example.duantotnghiep.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.UUID;


public interface SanPhamResponse {

    UUID getId();

    String getTenImage();

    String getTenSanPham();

    BigDecimal getGiaBan();




}
