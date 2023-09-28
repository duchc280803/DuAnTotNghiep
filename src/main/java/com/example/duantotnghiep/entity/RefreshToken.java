package com.example.duantotnghiep.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "refeshtoken")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "Token")
    private String token;

    @Column(name = "ThoiGianHetHan")
    private LocalDate thoiGianHetHan;

    @ManyToOne
    @JoinColumn(name = "taiKhoanId")
    private TaiKhoan taiKhoan;

}
