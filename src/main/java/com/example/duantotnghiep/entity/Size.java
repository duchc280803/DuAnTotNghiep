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
@Table
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "Size")
    private Integer size;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "size",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> sanPhamChiTietList;
}
