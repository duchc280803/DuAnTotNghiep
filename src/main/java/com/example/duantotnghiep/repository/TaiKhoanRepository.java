package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaiKhoanRepository extends JpaRepository<TaiKhoan, UUID> {

    Optional<TaiKhoan> findByUsername(String name);
}
