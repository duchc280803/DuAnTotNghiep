package com.example.duantotnghiep.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loaide")
public class LoaiDe {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tenloaide")
    private String tenLoaiDe;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "loaiDe", fetch = FetchType.LAZY)
    private List<KieuDe> kieuDeList;

}
