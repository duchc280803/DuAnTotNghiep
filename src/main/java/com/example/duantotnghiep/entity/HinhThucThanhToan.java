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
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hinhthucthanhtoan")
public class HinhThucThanhToan {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "ngaythanhtoan")
    private Date ngayThanhToan;

    @Column(name = "tongsotien")
    private Integer tongSoTien;

    @Column(name = "phuongthucthanhtoan")
    private Integer phuongThucThanhToan;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "trangthai")
    private Integer trangThai;

    @ManyToOne
    @JoinColumn(name = "idhoadon")
    @JsonBackReference
    private HoaDon hoaDon;

    @ManyToOne
    @JoinColumn(name = "idtaikhoan")
    @JsonBackReference
    private TaiKhoan taiKhoan;

    @ManyToOne
    @JoinColumn(name = "idloai")
    @JsonBackReference
    private LoaiHinhThucThanhToan loaiHinhThucThanhToan;
}
