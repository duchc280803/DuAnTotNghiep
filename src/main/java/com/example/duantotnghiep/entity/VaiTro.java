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
public class VaiTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private UUID id;

    @Column(name = "TenVaiTro")
    private String tenVaiTro;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "vaiTro",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<TaiKhoan> taiKhoanList;

}
