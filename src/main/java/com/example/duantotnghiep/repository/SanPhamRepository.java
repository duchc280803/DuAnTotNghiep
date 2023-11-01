package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.response.SanPhamResponse;
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


    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban FROM sanpham sp JOIN sanphamchitiet spct ON sp.id = spct.idsanpham JOIN \n" +
            "image im ON spct.id = im.idsanphamchitiet ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProduct();


        @Query("SELECT new com.example.duantotnghiep.response.SizeProductDetailResponse" +
                        "(spct.id, spct.size.size, spct.kieuDe.tenDe, spct.chatLieu.tenChatLieu, spct.mauSac.tenMauSac, spct.soLuong) from SanPhamChiTiet spct "
                        +
                        "where spct.sanPham.tenSanPham = :name")
        List<SizeProductDetailResponse> detailSizeProduct(@Param("name") String name);

        @Query("SELECT new com.example.duantotnghiep.response.QuantityDetailResponse(spct.soLuong) " +
                        "from SanPhamChiTiet spct WHERE spct.id = :id")
        QuantityDetailResponse quantityDetailResponse(@Param("id") UUID id);

}
