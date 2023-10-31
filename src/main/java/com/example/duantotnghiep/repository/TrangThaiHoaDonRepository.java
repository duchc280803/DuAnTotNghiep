package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TrangThaiHoaDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TrangThaiHoaDonRepository extends JpaRepository<TrangThaiHoaDon, UUID> {
}
