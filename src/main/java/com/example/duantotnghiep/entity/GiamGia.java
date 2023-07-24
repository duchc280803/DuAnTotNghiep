package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class GiamGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "MaGiamGia")
    private String maGiamGia;

    @Column(name = "TenGiamGia")
    private String tenGiamGia;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "HinhThucGiam")
    private Integer hinhThucGiam;

    @Column(name = "GiaTriGiam")
    private Integer giaTriGiam;

    @Column(name = "DieuKienGiamGia")
    private Integer dieuKienGiamGia;

    @Column(name = "LoaiGiamGia")
    private String loaiGiamGia;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "giamGia",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SpGiamGia> spGiamGiaList;
}
