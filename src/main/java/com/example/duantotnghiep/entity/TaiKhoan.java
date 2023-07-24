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
public class TaiKhoan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "IdVaiTro")
    @JsonBackReference
    private VaiTro vaiTro;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "MatKhau")
    private String matKhau;

    @Column(name = "Email")
    private String email;

    @Column(name = "Image")
    private String image;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @Column(name = "Ten")
    private String ten;

    @Column(name = "NgaySinh")
    private Date ngaySinh;

    @Column(name = "GioiTinh")
    private Boolean gioiTinh;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    private List<GioHang> gioHangList;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "taiKhoan",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

}
