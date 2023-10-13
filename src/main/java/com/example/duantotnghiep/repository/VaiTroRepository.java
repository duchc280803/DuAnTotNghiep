package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.enums.RoleEnum;
import com.example.duantotnghiep.entity.VaiTro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VaiTroRepository extends JpaRepository<VaiTro, UUID> {

    Optional<VaiTro> findByName(RoleEnum name);
}
