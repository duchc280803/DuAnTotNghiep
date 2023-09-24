package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GiamGia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MaGiamGiaRepository extends JpaRepository<GiamGia, UUID> {
}
