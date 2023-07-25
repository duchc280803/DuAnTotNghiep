package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giohangchitiet")
public class GioHangChiTiet {

    @Id
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idsanphamchitiet")
    @JsonBackReference
    private SanPhamChiTiet sanPhamChiTiet;

    @ManyToOne
    @JoinColumn(name = "idgiohang")
    @JsonBackReference
    private GioHang gioHang;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "dongia")
    private Integer donGia;

    @Column(name = "dongiakhigiam")
    private Integer donGiaKhiGiam;

    @Column(name = "trangthai")
    private Integer trangThai;

}
