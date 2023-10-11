package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.KhachHang;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.response.KhachHangResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<KhachHang, UUID> {

    @Query(value = "SELECT dbo.khachhang.hovaten, dbo.khachhang.email, dbo.khachhang.sodienthoai, dbo.khachhang.gioitinh, dbo.khachhang.ngaysinh, dbo.khachhang.trangthai, dbo.khachhang.id, dbo.diachi.diachi\n" +
            "FROM dbo.diachi INNER JOIN\n" +
            "dbo.khachhang ON dbo.diachi.idkhachhang = dbo.khachhang.id",
            nativeQuery = true)
    List<KhachHangMap> findlistKhachHang();

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.hoVaTen, kh.email, kh.soDienThoai, dc.diaChi) " +
            "FROM DiaChi dc " +
            "JOIN dc.khachHang kh " +
            "JOIN kh.hoaDonList hd " +
            "WHERE kh.id = :id AND hd.id = :idHoaDon")
    KhachHangResponse findByKhachHang(@Param("id") UUID id, @Param("idHoaDon") UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.hoVaTen, kh.email, kh.soDienThoai, dc.diaChi) " +
            "FROM KhachHang kh JOIN DiaChi dc WHERE kh.hoVaTen = :key OR kh.soDienThoai = :key OR kh.email = :key")
    KhachHangResponse findByKeyToKhachHang(@Param("key") String key);

}
