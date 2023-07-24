package com.example.duantotnghiep.entity;

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
public class KieuDe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "TenDe")
    private String tenDe;

    @Column(name = "LoaiDe")
    private String loaiDe;

    @Column(name = "MauSac")
    private String mauSac;

    @Column(name = "KichThuoc")
    private String kichThuoc;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "kieuDe",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> sanPhamChiTietList;

}
