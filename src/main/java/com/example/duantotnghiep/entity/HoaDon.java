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
@Table(name = "hoadon")
public class HoaDon {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idtaikhoan")
    @JsonBackReference
    private TaiKhoan taiKhoan;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngaytao")
    private Date ngayTao;

    @Column(name = "ngaythanhtoan")
    private Date ngayThanhToan;

    @Column(name = "ngayship")
    private Date ngayShip;

    @Column(name = "ngaynhan")
    private Date ngayNhan;

    @Column(name = "tennguoinhan")
    private String tenNguoiNhan;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "phantramgiamgia")
    private Long phanTramGiamGia;

    @Column(name = "sdtnguoinhan")
    private String sdtNguoiNhan;

    @Column(name = "sdtnguoiship")
    private String sdtNguoiShip;

    @Column(name = "tennguoiship")
    private String tenNguoiShip;

    @Column(name = "tienkhachtra")
    private Integer tienKhachTra;

    @Column(name = "tienship")
    private Integer tienShip;

    @Column(name = "tienthua")
    private Integer tienThua;

    @Column(name = "thanhtien")
    private Integer thanhTien;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDonChiTiet> hoaDonChiTietList;

}
