package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.response.HoaDonCustomResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
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

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonResponse(hd.id, hd.ma, tk.name, hd.trangThai)" +
            " FROM HoaDon hd JOIN hd.taiKhoanNhanVien tk WHERE hd.trangThai = 1")
    List<HoaDonResponse> viewHoaDonTaiQuay();

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonCustomResponse(hd.id, hd.ma, hd.ngayTao, hd.thanhTien, hd.trangThai, httt.phuongThucThanhToan, tknv.name, ld.trangThai)" +
            " FROM HoaDon hd JOIN hd.hinhThucThanhToan httt JOIN hd.taiKhoanNhanVien tknv JOIN hd.loaiDon ld")
    Page<HoaDonCustomResponse> getAllHoaDonAdmin(Pageable pageable);

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonCustomResponse(hd.id, hd.ma, hd.ngayTao, hd.thanhTien, hd.trangThai, httt.phuongThucThanhToan, tknv.name, ld.trangThai)" +
            " FROM HoaDon hd JOIN hd.hinhThucThanhToan httt JOIN hd.taiKhoanNhanVien tknv JOIN hd.loaiDon ld" +
            " WHERE (:trangThaiHD IS NULL OR hd.trangThai = :trangThaiHD)" +
            " AND (:phuongThucThanhToan IS NULL OR httt.phuongThucThanhToan = :phuongThucThanhToan) " +
            " AND (:loaiDon IS NULL OR ld.trangThai = :loaiDon) ")
    Page<HoaDonCustomResponse> getAllHoaDonAdminFilter(@Param("trangThaiHD") Integer trangThaiHD, @Param("phuongThucThanhToan") Integer phuongThucThanhToan, @Param("loaiDon") Integer loaiDon, Pageable pageable);


    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonCustomResponse(hd.id, hd.ma, hd.ngayTao, hd.thanhTien, hd.trangThai, httt.phuongThucThanhToan, tknv.name, ld.trangThai)" +
            " FROM HoaDon hd JOIN hd.hinhThucThanhToan httt JOIN hd.taiKhoanNhanVien tknv JOIN hd.loaiDon ld where hd.trangThai = 1 AND tknv.trangThai = 1")
    Page<HoaDonCustomResponse> getAllHoaDonEmployee(Pageable pageable);


    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonCustomResponse(hd.id, hd.ma, hd.ngayTao, hd.thanhTien, hd.trangThai, httt.phuongThucThanhToan, tknv.name, ld.trangThai)" +
            " FROM HoaDon hd JOIN hd.hinhThucThanhToan httt JOIN hd.taiKhoanNhanVien tknv JOIN hd.loaiDon ld" +
            " WHERE hd.trangThai <> 1 AND tknv.trangThai = 1 AND tknv.username = :username AND (:trangThaiHD IS NULL OR hd.trangThai = :trangThaiHD)" +
            " AND (:phuongThucThanhToan IS NULL OR httt.phuongThucThanhToan = :phuongThucThanhToan) AND (:loaiDon IS NULL OR ld.trangThai = :loaiDon)")
    Page<HoaDonCustomResponse> getAllHoaDonOfEmployeeFilter(
                                                        @Param("username") String username,
                                                        @Param("trangThaiHD") Integer trangThaiHD,
                                                        @Param("phuongThucThanhToan") Integer phuongThucThanhToan,
                                                        @Param("loaiDon") Integer loaiDon,
                                                        Pageable pageable);

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonCustomResponse(hd.id, hd.ma, hd.ngayTao, hd.thanhTien, hd.trangThai, httt.phuongThucThanhToan, tknv.name, ld.trangThai)" +
            " FROM HoaDon hd JOIN hd.hinhThucThanhToan httt JOIN hd.taiKhoanNhanVien tknv JOIN hd.loaiDon ld where hd.trangThai <> 1 AND tknv.trangThai = 1 AND tknv.username = :username")
    Page<HoaDonCustomResponse> getAllHoaDonOfEmployeeDefault( @Param("username") String username, Pageable pageable);









}
