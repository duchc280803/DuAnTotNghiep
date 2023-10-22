package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    @Query("SELECT new com.example.duantotnghiep.mapper.GioHangCustom(gh.id, i.tenImage, sp.tenSanPham, sp.giaBan, ghct.soLuong, s.size, kd.tenDe,ms.tenMauSac) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.listImage i " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.kieuDe kd " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND tk.name = :name")
    List<GioHangCustom> loadOnGioHang(@Param("name") String name);

    // Tìm mục trong giỏ hàng chi tiết dựa trên idGioHang và idSanPhamChiTiet
    @Query("select ghct from GioHangChiTiet ghct WHERE ghct.sanPhamChiTiet.id = :idSanPhamChiTiet")
    GioHangChiTiet findByGioHang(UUID idSanPhamChiTiet);

}
