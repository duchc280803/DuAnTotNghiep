package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hinhthucthanhtoan")
public class HinhThucThanhToan {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idhoadon")
    @JsonBackReference
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    @JsonBackReference
    private KhachHang khachHang;

    @Column(name = "ngaythanhtoan")
    private Date ngayThanhToan;

    @Column(name = "tongsotien")
    private BigDecimal tongSoTien;

    @Column(name = "phuongthucthanhtoan")
    private Integer phuongThucThanhToan;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "trangthai")
    private Integer trangThai;

}
