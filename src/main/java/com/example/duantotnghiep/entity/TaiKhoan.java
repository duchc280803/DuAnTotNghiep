package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
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

    @Column(name = "username")
    private String username;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ten")
    private String name;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @ManyToOne
    @JoinColumn(name = "idvaitro")
    @JsonBackReference
    @Enumerated(EnumType.STRING)
    private VaiTro vaiTro;

    @ManyToOne
    @JoinColumn(name = "idloaitaikhoan")
    @JsonBackReference
    private LoaiTaiKhoan loaiTaiKhoan;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GioHang> gioHangList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<RefreshToken> refreshTokenList;

}
