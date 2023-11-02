package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.Image;
import com.example.duantotnghiep.response.ProductDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImageRepository extends JpaRepository<Image, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.ProductDetailResponse(spct.id, i.tenImage, sp.tenSanPham) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.size s" +
            " JOIN spct.mauSac ms " +
            "JOIN spct.kieuDe kd " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i WHERE i.isDefault = true AND sp.id = :id ")
    List<ProductDetailResponse> listProductResponse(@Param("id") UUID id);
}
