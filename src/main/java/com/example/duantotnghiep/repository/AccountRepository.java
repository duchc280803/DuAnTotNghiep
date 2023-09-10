package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<TaiKhoan, UUID> {

    TaiKhoan findByNameOrEmail(String name,String email);

    Optional<TaiKhoan> findByUsername(String name);
}
