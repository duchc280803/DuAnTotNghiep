package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GioHangChiTiet {

    @ManyToOne
    @JoinColumn(name = "IdSanPhamChiTiet")
    @JsonBackReference
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "IdGioHang")
    @JsonBackReference
    private GioHang gioHang;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "DonGia")
    private Integer donGia;

    @Column(name = "DonGiaKhiGiam")
    private Integer donGiaKhiGiam;

    @Column(name = "TrangThai")
    private Integer trangThai;

}
