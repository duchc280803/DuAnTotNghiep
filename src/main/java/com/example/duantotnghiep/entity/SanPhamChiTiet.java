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
@Table
public class SanPhamChiTiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "IdSanPham")
    @JsonBackReference
    private SanPham sanPham;

    @ManyToOne
    @JoinColumn(name = "IdMauSac")
    @JsonBackReference
    private MauSac mauSac;

    @ManyToOne
    @JoinColumn(name = "IdChatLieu")
    @JsonBackReference
    private ChatLieu chatLieu;

    @ManyToOne
    @JoinColumn(name = "IdSize")
    @JsonBackReference
    private Size size;

    @ManyToOne
    @JoinColumn(name = "IdKieuDe")
    @JsonBackReference
    private KieuDe kieuDe;

    @Column(name = "BaoHanh")
    private Integer baoHanh;

    @Column(name = "GiaNhap")
    private Integer giaNhap;

    @Column(name = "GiaBan")
    private Integer giaBan;

    @Column(name = "SoLuong")
    private Integer soLuong;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SpGiamGia> spGiamGiaList;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GioHangChiTiet> gioHangChiTiets;

    @OneToMany(mappedBy = "sanPhamChiTiet",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDonChiTiet> hoaDonChiTietList;
}
