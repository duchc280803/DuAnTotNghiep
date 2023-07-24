package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class HoaDon {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "IdTaiKhoan")
    @JsonBackReference
    private TaiKhoan taiKhoan;

    @Column(name = "Ma")
    private String ma;

    @Column(name = "NgayTao")
    private Date ngayTao;

    @Column(name = "NgayThanhToan")
    private Date ngayThanhToan;

    @Column(name = "NgayShip")
    private Date ngayShip;

    @Column(name = "NgayNhan")
    private Date ngayNhan;

    @Column(name = "TenNguoiNhan")
    private String tenNguoiNhan;

    @Column(name = "DiaChi")
    private String diaChi;

    @Column(name = "PhanTramGiamGia")
    private Long phanTramGiamGia;

    @Column(name = "SDTNguoiNhan")
    private String sdtNguoiNhan;

    @Column(name = "SDTNguoiShip")
    private String sdtNguoiShip;

    @Column(name = "TenNguoiShip")
    private String tenNguoiShip;

    @Column(name = "TienKhachTra")
    private Integer tienKhachTra;

    @Column(name = "TienShip")
    private Integer tienShip;

    @Column(name = "TienThua")
    private Integer tienThua;

    @Column(name = "ThanhTien")
    private Integer thanhTien;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDonChiTiet> hoaDonChiTietList;

}
