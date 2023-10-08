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
@Table(name = "sanpham")
public class SanPham {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idthuonghieu")
    @JsonBackReference
    private ThuongHieu thuongHieu;

    @ManyToOne
    @JoinColumn(name = "iddanhmuc")
    @JsonBackReference
    private DanhMuc danhMuc;

    @ManyToOne
    @JoinColumn(name = "idxuatxu")
    @JsonBackReference
    private XuatXu xuatXu;

    @Column(name = "masanpham")
    private String maSanPham;

    @Column(name = "tensanpham")
    private String tenSanPham;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "sanPham",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> listSanPhamChiTiet;

}