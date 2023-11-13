package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoaiTaiKhoanRepository extends JpaRepository<LoaiTaiKhoan, UUID> {

    Optional<LoaiTaiKhoan> findByName(TypeAccountEnum name);


    @Query("SELECT lt FROM LoaiTaiKhoan lt WHERE lt.name = :manager OR lt.name = :staff OR lt.name = :admin")
    List<LoaiTaiKhoan> findByRoles(
            @Param("manager") TypeAccountEnum manager,
            @Param("staff") TypeAccountEnum staff,
            @Param("admin") TypeAccountEnum admin
    );
}
