package com.example.duantotnghiep.entity;

import com.example.duantotnghiep.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "chucvu")
public class ChucVu {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tenchucvu")
    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @Column(name = "trangthai")
    private Integer trangThai;

    @OneToMany(mappedBy = "chucVu",fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<NhanVien> taiKhoanList;

}
