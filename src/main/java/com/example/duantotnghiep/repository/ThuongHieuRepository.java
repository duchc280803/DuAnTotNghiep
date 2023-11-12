package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ThuongHieuRepository extends JpaRepository<ThuongHieu, UUID> {

    List<ThuongHieu> findByTrangThai(Integer trangThai);

    @Query("SELECT NEW com.example.duantotnghiep.entity.ThuongHieu(th.id, th.tenThuongHieu, th.trangThai)\n" +
            "FROM ThuongHieu th\n" +
            "WHERE (:trangThai IS NULL AND th.trangThai = 1) OR th.trangThai = :trangThai " +
            "AND (:tenThuongHieu IS NULL OR th.tenThuongHieu LIKE %:tenThuongHieu%)")
    Page<ThuongHieu> getAllThuongHieu(@Param("trangThai") Integer trangThai, @Param("tenThuongHieu") String tenThuongHieu, Pageable pageable);
}
