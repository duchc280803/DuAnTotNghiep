package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpGiamGiaRepository extends JpaRepository<SpGiamGia, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamGiamGiaResponse" +
            "(sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam) " +
            "FROM SanPham sp " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.listImage i " +
            "LEFT JOIN spct.spGiamGiaList spgg " +
            "LEFT JOIN spgg.giamGia gg " +
            "WHERE i.isDefault = true AND (:tenSanPham IS NULL OR sp.tenSanPham LIKE %:tenSanPham%) " +
            "AND (spgg IS NOT NULL AND spgg.donGiaKhiGiam <> 0) " +
            "OR spgg IS NULL")
    List<SanPhamGiamGiaResponse> getAllSpGiamGia(@Param("tenSanPham") String tenSanPham);

}
