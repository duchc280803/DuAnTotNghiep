package com.example.duantotnghiep.entity;

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
@Table(name = "kieude")
public class KieuDe {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tende")
    private String tenDe;

    @Column(name = "mausac")
    private String mauSac;

    @Column(name = "kichthuoc")
    private String kichThuoc;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "kieuDe",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> sanPhamChiTietList;

    @ManyToOne
    @JoinColumn(name = "loaide")
    private LoaiDe loaiDe;
}
