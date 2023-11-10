package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {

    HoaDonChiTiet findByHoaDonAndSanPhamChiTiet_Id(HoaDon hoaDon, UUID idSpCt);

    @Query(value = "SELECT TOP 1 hd.trangthai, LD.tenloaidon,\n" +
            "HD.diachi,\n" +
            "HD.tennguoinhan, HD.sdtnguoinhan, TTHD.ghichu\n" +
            "FROM\n" +
            "hoadon HD JOIN loaidon LD ON HD.idloaidon = LD.id\n" +
            "JOIN trangthaihoadon TTHD ON TTHD.idhoadon = HD.id\n" +
            "WHERE TTHD.idhoadon = ?1\n" +
            "ORDER BY TTHD.thoigian DESC", nativeQuery = true)
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);


    @Query("SELECT new com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse" +
            "(hdct.id, i.tenImage, sp.tenSanPham, hdct.donGia, hdct.donGiaSauGiam, hdct.soLuong) " +
            "FROM HoaDon hd JOIN " +
            "hd.hoaDonChiTietList hdct " +
            "JOIN hdct.sanPhamChiTiet spct" +
            " JOIN spct.sanPham sp " +
            "JOIN sp.listImage i " +
            "WHERE i.isDefault = true AND hd.id = :idHoaDon")
    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(@Param("idHoaDon") UUID idHoaDon);

    @Query(value = "SELECT HD.ma, HTTT.sotientra, HTTT.ngaytao, LHTT.tenloai, HTTT.phuongthucthanhtoan, HTTT.ghichu, HTTT.trangthai, TKNV.fullname FROM hoadon HD\n" +
            "LEFT JOIN hinhthucthanhtoan HTTT ON HD.id = HTTT.idhoadon LEFT JOIN loaihinhthucthanhtoan LHTT ON HTTT.idloai = LHTT.id\n" +
            "JOIN taikhoan TKNV ON HD.idnhanvien = TKNV.id WHERE HD.id = ?1\n", nativeQuery = true)
    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.TrangThaiHoaDonResponse(tthd.trangThai, tthd.thoiGian, tknv.name, tthd.ghiChu) " +
            "FROM HoaDon hd " +
            "JOIN hd.trangThaiHoaDonList tthd " +
            "JOIN hd.taiKhoanNhanVien tknv WHERE hd.id = :id ORDER BY tthd.thoiGian ASC ")
    List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(@Param("id") UUID id);



}
