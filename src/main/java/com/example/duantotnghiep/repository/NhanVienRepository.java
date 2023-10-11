package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.NhanVien;
import com.example.duantotnghiep.response.NhanVienResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.NhanVienResponse(nv.id, nv.username, nv.email, nv.hoVaTen, nv.trangThai, nv.chucVu.name) " +
            "FROM NhanVien nv WHERE nv.username = :username")
    NhanVienResponse findByNhanVien(String username);

    Optional<NhanVien> findByUsername(String name);

}
