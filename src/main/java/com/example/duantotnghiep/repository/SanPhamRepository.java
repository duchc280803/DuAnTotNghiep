package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.ProductDetailResponse" +
            "(sp.id, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam) " +
            "FROM SanPham sp " +
            "LEFT JOIN sp.spGiamGiaList spgg " +
            "LEFT JOIN spgg.giamGia gg " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "LEFT JOIN spct.listImage i " +
            "WHERE i.isDefault = true AND sp.tenSanPham = :name")
    ProductDetailResponse detailProduct(@Param("name") String name);

    @Query("SELECT new com.example.duantotnghiep.response.SizeProductDetailResponse" +
            "(spct.id, spct.size.size, spct.kieuDe.tenDe, spct.chatLieu.tenChatLieu, spct.mauSac.tenMauSac, spct.soLuong) from SanPhamChiTiet spct " +
            "where spct.sanPham.tenSanPham = :name")
    List<SizeProductDetailResponse> detailSizeProduct(@Param("name") String name);

    @Query("SELECT new com.example.duantotnghiep.response.QuantityDetailResponse(spct.soLuong) " +
            "from SanPhamChiTiet spct WHERE spct.id = :id")
    QuantityDetailResponse quantityDetailResponse(@Param("id") UUID id);
}
