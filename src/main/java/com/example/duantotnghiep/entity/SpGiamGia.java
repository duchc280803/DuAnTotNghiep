package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "spgiamgia")
public class SpGiamGia {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idsanpham")
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "idgiamgia")
    private GiamGia giamGia;

    @Column(name = "soluongma")
    private Integer soLuongMa;

    @Column(name = "soluotdung")
    private Integer soLuongDung;

    @Column(name = "dongia")
    private BigDecimal donGia;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "gioihansotien")
    private BigDecimal gioiHanSoTien;


}
