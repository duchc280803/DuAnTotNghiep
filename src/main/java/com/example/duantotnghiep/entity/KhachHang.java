package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "khachhang")
public class KhachHang {

    @Id
    private UUID id;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "email")
    private String email;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "anh")
    private String image;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DiaChi> diaChiList;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "khachHang", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GioHang> gioHangList;

    @ManyToOne
    @JoinColumn(name = "idtaikhoan")
    @JsonBackReference
    private TaiKhoan taiKhoan;

}
