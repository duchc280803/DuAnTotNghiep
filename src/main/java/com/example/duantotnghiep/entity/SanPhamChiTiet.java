package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sanphamchitiet")
public class SanPhamChiTiet {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idsanpham")
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "idmausac")
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "idchatlieu")
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "idsize")
    private Size size;

    @ManyToOne
    @JoinColumn(name = "idkieude")
    private KieuDe kieuDe;

    @Column(name = "baohanh")
    private Integer baoHanh;

    @Column(name = "gianhap")
    private Integer giaNhap;

    @Column(name = "giaban")
    private Integer giaBan;

    @Column(name = "soluong")
    private Integer soLuong;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    private List<SpGiamGia> spGiamGiaList;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    private List<GioHangChiTiet> gioHangChiTiets;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    private List<HoaDonChiTiet> hoaDonChiTietList;
}
