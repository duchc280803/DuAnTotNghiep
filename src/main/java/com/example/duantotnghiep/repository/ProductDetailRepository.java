package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.response.ProductDetailResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductDetailRepository extends JpaRepository<SanPhamChiTiet, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.ProductDetailResponse(spct.id, i.tenImage, sp.tenSanPham) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.size s" +
            " JOIN spct.mauSac ms " +
            "JOIN spct.kieuDe kd " +
            "JOIN spct.sanPham sp " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listImage i WHERE i.isDefault = true GROUP BY spct.id, i.tenImage, sp.tenSanPham")
    List<ProductDetailResponse> listProductDetailResponse();

}
