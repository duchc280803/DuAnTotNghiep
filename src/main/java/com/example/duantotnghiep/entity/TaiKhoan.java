package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "taikhoan")
public class TaiKhoan {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "ten")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @Column(name = "username")
    private String username;

    @Column(name = "matkhau")
    private String password;

    @Column(name = "image")
    private String image;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "idvaitro")
    @JsonBackReference
    @Enumerated(EnumType.STRING)
    private VaiTro vaiTro;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RefreshToken> refreshTokenList;

    @ManyToOne
    @JoinColumn(name = "idloaitaikhoan")
    @JsonBackReference
    private LoaiTaiKhoan loaiTaiKhoan;

    @OneToMany(mappedBy = "taiKhoanKhachHang", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> khachHangToHoaDonList;

    @OneToMany(mappedBy = "taiKhoanNhanVien", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> nhanVienToHoaDonList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<DiaChi> diaChiList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;
}
