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

//    @Query("SELECT new com.example.duantotnghiep.response.(spct.id, i.tenImage, sp.tenSanPham,s.size,kd.tenDe,ms.tenMauSac,ct.tenChatLieu,sp.trangThai) " +
//            "FROM SanPhamChiTiet spct " +
//            "JOIN spct.size s" +
//            " JOIN spct.mauSac ms " +
//            "JOIN spct.kieuDe kd " +
//            "JOIN spct.sanPham sp " +
//            "JOIN sp.thuongHieu th " +
//            "JOIN sp.danhMuc dm " +
//            "JOIN sp.xuatXu xx " +
//            "JOIN spct.chatLieu ct " +
//            "JOIN spct.listImage i WHERE i.isDefault = true GROUP BY spct.id, i.tenImage, sp.tenSanPham")
//    List<ProductDetailResponse> listProductDetailResponse();

}
