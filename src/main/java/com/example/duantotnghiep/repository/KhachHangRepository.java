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

    @Query(value = "SELECT TK.id, TK.email, TK.trangthai, TK.hovaten,\n" +
            "TK.ngaysinh, TK.gioitinh, TK.sodienthoai FROM khachhang TK",
            nativeQuery = true)
    List<KhachHangMap> findlistKhachHang();

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.hoVaTen, kh.email, kh.soDienThoai) " +
            "FROM KhachHang kh ")
    KhachHangResponse findByKhachHang(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.hoVaTen, kh.email, kh.soDienThoai) " +
            "FROM KhachHang kh WHERE kh.hoVaTen = :key OR kh.soDienThoai = :key OR kh.email = :key")
    KhachHangResponse findByKeyToKhachHang(@Param("key") String key);

}
