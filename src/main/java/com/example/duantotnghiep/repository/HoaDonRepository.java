package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;

import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;

import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;

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
            " FROM HoaDon hd JOIN hd.loaiDon ld JOIN hd.taiKhoanNhanVien tk WHERE hd.trangThai = 1 AND ld.trangThai = 2")
    List<HoaDonResponse> viewHoaDonTaiQuay();

    // TODO Hiển thị hóa đơn của Admin
    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonDTOResponse(hd.id, hd.ma, tkkh.name, tkkh.soDienThoai, hd.thanhTien, SUM(hdct.tienGiamGia), hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai)\n" +
            "FROM HoaDon hd\n" +
            "LEFT JOIN hd.hoaDonChiTietList hdct\n" +
            "LEFT JOIN hd.taiKhoanKhachHang tkkh\n" +
            "LEFT JOIN hd.taiKhoanNhanVien tknv\n" +
            "JOIN hd.loaiDon ld\n" +
            "WHERE (:trangThaiHD IS NULL OR hd.trangThai = :trangThaiHD) AND (:loaiDon IS NULL OR ld.trangThai = :loaiDon) AND (:tenNhanVien IS NULL OR tknv.name = :tenNhanVien)" +
            "AND (:ma IS NULL OR hd.ma LIKE %:ma%) AND (:soDienThoai IS NULL OR tkkh.soDienThoai LIKE %:soDienThoai%)\n" +
            "GROUP BY hd.id, hd.ma, tkkh.name, tkkh.soDienThoai, hd.thanhTien, hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai\n" +
            "ORDER BY hd.ngayTao DESC")
    Page<HoaDonDTOResponse> getAllHoaDonAdmin(@Param("trangThaiHD") Integer trangThaiHD, @Param("loaiDon") Integer loaiDon, @Param("tenNhanVien") String tenNhanVien, @Param("ma") String ma, @Param("soDienThoai") String soDienThoai, Pageable pageable);

    // TODO Hiển thị hóa đơn Staff
    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonDTOResponse(hd.id, hd.ma, tkkh.name, tkkh.soDienThoai,hd.thanhTien, SUM(hdct.tienGiamGia), hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai)\n" +
            "FROM HoaDon hd\n" +
            "LEFT JOIN hd.hoaDonChiTietList hdct\n" +
            "LEFT JOIN hd.taiKhoanKhachHang tkkh\n" +
            "LEFT JOIN hd.taiKhoanNhanVien tknv\n" +
            "JOIN hd.loaiDon ld\n" +
            "WHERE (:trangThaiHD IS NULL OR hd.trangThai = :trangThaiHD) AND (:loaiDon IS NULL OR ld.trangThai = :loaiDon) " +
            "AND (:ma IS NULL OR hd.ma LIKE %:ma%) AND (:soDienThoai IS NULL OR tkkh.soDienThoai LIKE %:soDienThoai%) AND tknv.username = :username\n" +
            "GROUP BY hd.id, hd.ma, tkkh.name, tkkh.soDienThoai, hd.thanhTien, hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai\n" +
            "ORDER BY hd.ngayTao DESC")
    Page<HoaDonDTOResponse> getAllHoaDonStaff(@Param("trangThaiHD") Integer trangThaiHD, @Param("loaiDon") Integer loaiDon, @Param("ma") String ma, @Param("soDienThoai") String soDienThoai, @Param("username") String username, Pageable pageable);

    // TODO Hiển thị hóa đơn chưa thanh toán cho staff
    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonDTOResponse(hd.id, hd.ma, tkkh.name, tkkh.soDienThoai, hd.thanhTien, SUM(hdct.tienGiamGia), hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai)\n" +
            "FROM HoaDon hd\n" +
            "LEFT JOIN hd.hoaDonChiTietList hdct\n" +
            "LEFT JOIN hd.taiKhoanKhachHang tkkh\n" +
            "LEFT JOIN hd.taiKhoanNhanVien tknv\n" +
            "JOIN hd.loaiDon ld\n" +
            "WHERE hd.trangThai = 1 AND (:loaiDon IS NULL OR ld.trangThai = :loaiDon) " +
            "AND (:ma IS NULL OR hd.ma LIKE %:ma%) AND (:soDienThoai IS NULL OR tkkh.soDienThoai LIKE %:soDienThoai%) " +
            "GROUP BY hd.id, hd.ma, tkkh.name, tkkh.soDienThoai, hd.thanhTien, hd.ngayTao, tknv.name, ld.tenLoaiDon, hd.trangThai\n" +
            "ORDER BY hd.ngayTao DESC")
    Page<HoaDonDTOResponse> getAllHoaDonCTTStaff(@Param("loaiDon") Integer loaiDon, @Param("ma") String ma, @Param("soDienThoai") String soDienThoai, Pageable pageable);


    @Query(value = "SELECT TOP 1 TTHD.trangthai, LD.tenloaidon,\n" +
            "CONCAT(DC.diachi, ' - ', DC.Xa, ' - ', DC.huyen, ' - ', DC.tinh, ' - ', DC.quocgia) as diachi,\n" +
            "TKKH.fullname, TKKH.sodienthoai, TTHD.ghichu\n" +
            "FROM\n" +
            "hoadon HD JOIN loaidon LD ON HD.idloaidon = LD.id\n" +
            "JOIN trangthaihoadon TTHD ON TTHD.idhoadon = HD.id\n" +
            "LEFT JOIN taikhoan TKKH ON TKKH.id = HD.idkhachhang \n" +
            "LEFT JOIN diachi DC ON DC.idtaikhoan = TKKH.id\n" +
            "WHERE TTHD.idhoadon = ?1\n" +
            "ORDER BY TTHD.thoigian DESC", nativeQuery = true)
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);



}
