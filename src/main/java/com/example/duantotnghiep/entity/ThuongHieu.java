package com.example.duantotnghiep.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class ThuongHieu {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "TenThuongHieu")
    @JsonBackReference
    private String tenThuongHieu;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "TrangThai")
    private String trangThai;

    @OneToMany(mappedBy = "thuongHieu",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPham> sanPhamList;

}
