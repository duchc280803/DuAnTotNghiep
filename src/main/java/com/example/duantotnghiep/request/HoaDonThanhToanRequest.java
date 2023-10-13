package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonThanhToanRequest {

    private UUID idHoaDon;

    private Date ngayNhan;

    private BigDecimal tienThua;

    private BigDecimal tongTien;

    private BigDecimal tienKhachTra;

    private List<UUID> gioHangChiTietList;
}
