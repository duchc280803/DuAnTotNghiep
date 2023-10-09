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
@Table(name = "hoadonchitiet")
public class HoaDonChiTiet {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idsanphamchitiet")
    @JsonBackReference
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "idhoadon")
    @JsonBackReference
    private HoaDon hoaDon;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "dongia")
    private Integer donGia;

    @Column(name = "tiengiamgia")
    private Integer tienGiamGia;

    @Column(name = "comment")
    private String comment;

    @Column(name = "trangthai")
    private Integer trangThai;

}
