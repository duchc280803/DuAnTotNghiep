package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, UUID> {

    Optional<NhanVien> findByUsername(String name);

    NhanVien findByHoVaTen(String name);
}
