package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "giamgia")
public class GiamGia {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "magiamgia")
    private String maGiamGia;

    @Column(name = "tengiamgia")
    private String tenGiamGia;

    @Column(name = "ngaybatdau")
    private Date ngayBatDau;

    @Column(name = "ngayketthuc")
    private Date ngayKetThuc;

    @Column(name = "hinhthucgiam")
    private Integer hinhThucGiam;

    @Column(name = "giatrigiamtu")
    private Integer giaTriGiamTu;

    @Column(name = "giatrigiamden")
    private Integer giaTriGiamDen;

    @Column(name = "dieukiengiamgia")
    private Integer dieuKienGiamGia;

    @Column(name = "soluongmagiamgia")
    private Integer soLuongMaGiaGiam;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "giamGia",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SpGiamGia> spGiamGiaList;

    @OneToMany(mappedBy = "giamGia",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

    @ManyToOne
    @JoinColumn(name = "loaigiamgia")
    @JsonBackReference
    private LoaiGiamGia loaiGiamGia;
}
