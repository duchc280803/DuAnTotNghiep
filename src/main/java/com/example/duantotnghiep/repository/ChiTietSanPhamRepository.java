package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.DetailSizeToProductResponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<SanPhamChiTiet, UUID> {

    @Query("SELECT s.giaBan - COALESCE(SUM(gg.mucGiam), 0) - COALESCE(SUM(gg.donGiaKhiGiam), 0)AS GiaGocMinusGiaCuoiCuong " +
            "FROM SanPham s " +
            "LEFT JOIN s.spGiamGiaList gg " +
            "WHERE s.id = :id AND gg.trangThai = 1 " +
            "GROUP BY s.giaBan")
    BigDecimal calculatePriceDifference(@Param("id") UUID id);

    // TODO: Load sản phẩm ở quầy
    @Query("SELECT sp.id, spct.id, i.tenImage, sp.tenSanPham, sp.giaBan, spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1")
    List<Object[]> getAll();

    //     tìm kiếm sản phẩm theo tên
    @Query("SELECT spct.id,i.tenImage, sp.tenSanPham, sp.giaBan,spct.soLuong, ms.tenMauSac, s.size, cl.tenChatLieu " +
            "FROM SanPham sp " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE " +
            "AND kd.trangThai = 1 " +
            "AND sp.trangThai = 1 " +
            "AND th.trangThai = 1 " +
            "AND dm.trangThai = 1 " +
            "AND xx.trangThai = 1 " +
            "AND spct.trangThai = 1 " +
            "AND s.trangThai = 1 " +
            "AND cl.trangThai = 1 " +
            "AND ms.trangThai = 1 " +
            "AND sp.tenSanPham = :name")
    List<Object[]> searchByName(String name);

    @Query("SELECT new com.example.duantotnghiep.response.SanPhamGetAllResponse(" +
            "sp.id, sp.tenSanPham, i.tenImage, th.tenThuongHieu, dm.tenDanhMuc, xx.tenXuatXu, sp.giaBan) " +
            "FROM SanPham sp " +
            "JOIN sp.danhMuc dm " +
            "JOIN sp.xuatXu xx " +
            "JOIN sp.thuongHieu th " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN sp.listImage i " +
            "WHERE sp.id = :id AND i.isDefault = true ")
    SanPhamGetAllResponse getByIdSp(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.DetailQuantityToSizeReponse(spct.soLuong)" +
            " FROM SanPhamChiTiet spct WHERE spct.sanPham.id = :id AND spct.size.size = :size")
    DetailQuantityToSizeReponse getDetailQuantityToSizeReponse(@Param("id") UUID id, @Param("size") Integer size);

}
