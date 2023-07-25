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
@Table(name = "spgiamgia")
public class SpGiamGia {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idsanpham")
    @JsonBackReference
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "idgiamgia")
    @JsonBackReference
    private GiamGia giamGia;

    @Column(name = "soluongma")
    private Integer soLuongMa;

    @Column(name = "dongia")
    private Integer donGia;

    @Column(name = "trangthai")
    private Integer trangThai;

}
