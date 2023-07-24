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
public class SanPham {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "IdThuongHieu")
    @JsonBackReference
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "IdDanhMuc")
    @JsonBackReference
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "IdXuatXu")
    @JsonBackReference
    private XuatXu xuatXu;

    @Column(name = "MaSanPham")
    private String maSanPham;

    @Column(name = "TenSanPham")
    private String tenSanPham;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "sanPham",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Image> listImage;

    @OneToMany(mappedBy = "sanPham",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> listSanPhamChiTiet;

}
