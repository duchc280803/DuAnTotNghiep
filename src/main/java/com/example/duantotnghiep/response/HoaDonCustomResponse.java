package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter


public class HoaDonCustomResponse {
    private UUID id;

    private String ma;

    private Date ngayTao;

    private BigDecimal thanhTien;

    private int trangThai;

    private int hinhThucThanhToan;

    private String nguoiTao;

    private int trangThaiLoaiDon;
}
