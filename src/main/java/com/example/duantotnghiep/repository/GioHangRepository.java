package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GioHangRepository extends JpaRepository<GioHang, UUID> {
    // Tìm giỏ hàng theo ID tài khoản
//    Optional<GioHang> findByKhachHang_Id(UUID taiKhoanId);
}
