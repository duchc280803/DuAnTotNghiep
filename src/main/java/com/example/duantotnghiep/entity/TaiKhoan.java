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
@Table(name = "taikhoan")
public class TaiKhoan {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "idvaitro")
    @JsonBackReference
    private VaiTro vaiTro;

    @Column(name = "username")
    private String userName;

    @Column(name = "matkhau")
    private String matKhau;

    @Column(name = "email")
    private String email;

    @Column(name = "image")
    private String image;

    @Column(name = "trangthai")
    private Integer trangThai;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ngaysinh")
    private Date ngaySinh;

    @Column(name = "gioitinh")
    private Boolean gioiTinh;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<GioHang> gioHangList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private List<HinhThucThanhToan> hinhThucThanhToanList;

    @OneToMany(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

    public TaiKhoan(UUID id,String userName, String matKhau, String email) {
        this.id = id;
        this.userName = userName;
        this.matKhau = matKhau;
        this.email = email;
    }

}
