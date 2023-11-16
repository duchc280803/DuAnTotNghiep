package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban FROM sanpham sp JOIN sanphamchitiet spct ON sp.id = spct.idsanpham JOIN \n" +
            "            image im ON sp.id = im.idsanpham WHERE im.isdefault = 1 AND sp.trangthai = 1 ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProduct();

    @Query("SELECT NEW com.example.duantotnghiep.response.SanPhamDTOResponse(sp.id, sp.maSanPham, sp.tenSanPham, im.tenImage, sp.giaBan, sp.ngayTao, sp.ngayCapNhat)\n" +
            "FROM SanPham sp JOIN sp.listImage im\n" +
            "JOIN sp.danhMuc dm JOIN sp.thuongHieu th \n" +
            "JOIN sp.kieuDe kd JOIN sp.xuatXu xx \n" +
            "WHERE im.isDefault = true AND (:idDanhMuc IS NULL OR dm.id = :idDanhMuc) AND (:idThuongHieu IS NULL OR th.id = :idThuongHieu) " +
            "AND (:idKieuDe IS NULL OR kd.id = :idKieuDe) AND (:idXuatXu IS NULL OR xx.id = :idXuatXu)" +
            "AND (:maSanPham IS NULL OR sp.maSanPham LIKE %:maSanPham%) AND (:tenSanPham IS NULL OR sp.tenSanPham LIKE %:tenSanPham%) " +
            "AND (:trangThai IS NULL OR sp.trangThai = :trangThai)\n" +
            "ORDER BY sp.ngayTao DESC")
    Page<SanPhamDTOResponse> getHoaDonByFilter(@Param("trangThai") Integer trangThai,
                                               @Param("idDanhMuc") UUID idDanhMuc,
                                               @Param("idThuongHieu") UUID idThuongHieu,
                                               @Param("idKieuDe") UUID idKieuDe,
                                               @Param("idXuatXu") UUID idXuatXu,
                                               @Param("maSanPham") String maSanPham,
                                               @Param("tenSanPham") String tenSanPham,
                                               Pageable pageable);
}
