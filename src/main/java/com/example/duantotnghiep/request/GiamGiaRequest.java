package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiamGiaRequest {

    private String maGiamGia;

    private String tenGiamGia;

    private String loaiGiamGia;

    private Integer hinhThucGiamgGia;

    private Integer soLuong;

    private Integer soLuongSuDung;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer trangThai;
}
