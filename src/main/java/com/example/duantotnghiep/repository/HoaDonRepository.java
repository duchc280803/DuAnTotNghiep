package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonResponse(hd.id, hd.ma, tknv.name, hd.ngayTao, tkkh.name, ld.tenLoaiDon, hd.trangThai)" +
            "FROM HoaDon hd " +
            "JOIN hd.loaiDon ld " +
            "JOIN hd.taiKhoanNhanVien tknv " +
            "LEFT JOIN hd.taiKhoanKhachHang tkkh " +
            "WHERE hd.trangThai = 1 AND ld.trangThai = 2 " +
            "ORDER BY hd.ngayTao DESC")
    Page<HoaDonResponse> viewHoaDonTaiQuay(Pageable pageable);

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonResponse(hd.id, hd.ma, tknv.name, hd.ngayTao, tkkh.name, ld.tenLoaiDon, hd.trangThai)" +
            " FROM HoaDon hd " +
            "JOIN hd.loaiDon ld " +
            "JOIN hd.taiKhoanNhanVien tknv " +
            "LEFT JOIN hd.taiKhoanKhachHang tkkh " +
            "WHERE hd.trangThai = 1 AND ld.trangThai = 2 AND hd.ma = :ma " +
            "ORDER BY hd.ngayTao DESC")
    List<HoaDonResponse> findByCodeOrder(@Param("ma") String ma);

    @Query("SELECT new com.example.duantotnghiep.response.IdGioHangResponse(gh.id) " +
            "FROM HoaDon hd " +
            "JOIN hd.taiKhoanKhachHang tk " +
            "JOIN tk.gioHangList gh WHERE hd.trangThai = 1 AND tk.name = :name")
    IdGioHangResponse showIdGioHangCt(@Param("name") String name);
}
