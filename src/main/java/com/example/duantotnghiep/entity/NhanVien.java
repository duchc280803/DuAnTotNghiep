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
@Table(name = "nhanvien")
public class NhanVien {

    @Id
    @Column(name = "id")
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

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    private List<DiaChi> diaChiList;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<HoaDon> hoaDonList;

    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GioHang> gioHangList;

    @ManyToOne
    @JoinColumn(name = "idtaikhoan")
    @JsonBackReference
    private TaiKhoan taiKhoan;

}
