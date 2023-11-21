package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConfirmOrderClientRequest {

    private String hoVaTenNguoiShip;

    private String soDienThoai;

    private BigDecimal tienShip;

    private String diaChi;
}
