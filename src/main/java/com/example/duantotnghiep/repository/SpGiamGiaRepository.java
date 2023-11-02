package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpGiamGiaRepository extends JpaRepository<SpGiamGia, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamGiamGiaResponse" +
            "(sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam) " +
            "FROM SanPham sp " +
            "LEFT JOIN sp.spGiamGiaList spgg " +
            "LEFT JOIN spgg.giamGia gg " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true")
    List<SanPhamGiamGiaResponse> getAllSpGiamGia();

}
