package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.XuatXu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface XuatSuRepository extends JpaRepository<XuatXu, UUID> {

    List<XuatXu> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.XuatXu(th.id, th.tenXuatXu, th.trangThai)\n" +
            "FROM XuatXu th\n" +
            "WHERE (:trangThai IS NULL AND th.trangThai = 1) OR th.trangThai = :trangThai " +
            "AND (:tenXuatXu IS NULL OR th.tenXuatXu LIKE %:tenXuatXu%)")
    Page<XuatXu> getAllXuatXu(@Param("trangThai") Integer trangThai, @Param("tenXuatXu") String tenXuatXu, Pageable pageable);
}
