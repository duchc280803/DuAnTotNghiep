package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(name = "password")
    private String password;

    @Column(name = "avatar")
    private String image;

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

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<KhachHang> khachHangList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<NhanVien> nhanVienList;
}
