package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

    @Column(name = "tienthachtra")
    private BigDecimal tienKhachTra;

    @Column(name = "tienship")
    private BigDecimal tienShip;

    @Column(name = "tienthua")
    private BigDecimal tienThua;

    @Column(name = "thanhtien")
    private BigDecimal thanhTien;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "qrcode")
    private String qrcode;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "hoaDon",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDonChiTiet> hoaDonChiTietList;

    @ManyToOne
    @JoinColumn(name = "idgiamgia")
    @JsonBackReference
    private GiamGia giamGia;

    @ManyToOne
    @JoinColumn(name = "idkhachhang")
    @JsonBackReference
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(name = "idnhanvien")
    @JsonBackReference
    private NhanVien nhanVien;

}
