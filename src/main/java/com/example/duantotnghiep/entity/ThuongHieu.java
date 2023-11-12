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
@Table(name = "thuonghieu")
public class ThuongHieu {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tenthuonghieu")
    private String tenThuongHieu;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "thuongHieu", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPham> sanPhamList;

    public ThuongHieu(UUID id, String tenThuongHieu, Integer trangThai) {
        this.id = id;
        this.tenThuongHieu = tenThuongHieu;
        this.trangThai = trangThai;
    }
}
