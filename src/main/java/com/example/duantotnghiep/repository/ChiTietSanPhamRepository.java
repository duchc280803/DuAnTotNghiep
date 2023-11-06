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
import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<SanPhamChiTiet, UUID> {
    // load sản phẩm với 1 ảnh mặc định
    @Query("SELECT new com.example.duantotnghiep.mapper.ChiTietSanPhamCustom" +
            "(i.tenImage, spct.id, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam,spct.soLuong, kd.tenDe, ms.tenMauSac, s.size, cl.tenChatLieu) " +
            "FROM SanPham sp " +
            "LEFT JOIN sp.spGiamGiaList spgg " +
            "LEFT JOIN spgg.giamGia gg " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE ")
    List<ChiTietSanPhamCustom> getAll();

    // tìm kiếm sản phẩm theo tên
    @Query("SELECT new com.example.duantotnghiep.mapper.ChiTietSanPhamCustom" +
            "(i.tenImage, spct.id, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam,spct.soLuong, kd.tenDe, ms.tenMauSac, s.size, cl.tenChatLieu) "
            +
            "FROM SanPham sp " +
            "LEFT JOIN sp.spGiamGiaList spgg " +
            "LEFT JOIN spgg.giamGia gg " +
            "JOIN sp.listSanPhamChiTiet spct " +
            "JOIN sp.listImage i " +
            "JOIN sp.kieuDe kd " +
            "JOIN spct.size s " +
            "JOIN spct.chatLieu cl " +
            "JOIN spct.mauSac ms " +
            "WHERE i.isDefault = TRUE AND sp.tenSanPham = :name")
    List<ChiTietSanPhamCustom> searchByName(String name);

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
