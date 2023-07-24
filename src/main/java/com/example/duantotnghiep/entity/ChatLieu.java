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
public class ChatLieu {

    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "TenChatLieu")
    private String tenChatLieu;

    @Column(name = "TrangThai")
    private Integer trangThai;

    @OneToMany(mappedBy = "chatLieu",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<SanPhamChiTiet> sanPhamChiTietList;

}
