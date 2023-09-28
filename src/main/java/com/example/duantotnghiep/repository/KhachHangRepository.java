package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.TaiKhoanMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<TaiKhoan, UUID> {
    @Query(value = "SELECT [Email]\n" +
            "      ,[Image]\n" +
            "      ,[TrangThai]\n" +
            "      ,[Ten]\n" +
            "      ,[NgaySinh]\n" +
            "      ,[GioiTinh]\n" +
            "  FROM [dbo].[TaiKhoan] WHERE IdVaiTro = '9EAFAA7A-C2AE-4889-807E-3B668E6EDAED'", nativeQuery = true)
    List<TaiKhoanMap> findlistKhachHang();

}
