package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GioHangChiTietRepository extends JpaRepository<GioHangChiTiet, UUID> {

    @Query("SELECT new com.example.duantotnghiep.mapper.GioHangCustom(ghct.id, i.tenImage, sp.tenSanPham, sp.giaBan, ghct.soLuong, s.size, kd.tenDe,ms.tenMauSac) " +
            "FROM SanPhamChiTiet spct " +
            "JOIN spct.listImage i " +
            "JOIN spct.sanPham sp " +
            "JOIN spct.size s " +
            "JOIN spct.kieuDe kd " +
            "JOIN spct.mauSac ms " +
            "JOIN spct.gioHangChiTietList ghct " +
            "JOIN ghct.gioHang gh " +
            "JOIN gh.taiKhoan tk " +
            "WHERE i.isDefault = true AND tk.id = :id")
    Page<GioHangCustom> loadOnGioHang(@Param("id") UUID id, Pageable pageable);

    // Tìm mục trong giỏ hàng chi tiết dựa trên idGioHang và idSanPhamChiTiet
    GioHangChiTiet findByGioHangAndSanPhamChiTiet_Id(GioHang gioHang, UUID idSanPhamChiTiet);

}
