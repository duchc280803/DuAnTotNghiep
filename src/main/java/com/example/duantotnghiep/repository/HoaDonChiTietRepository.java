package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDonChiTiet;
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
public interface HoaDonChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    @Query(value = "SELECT TOP 1 TTHD.trangthai, LD.tenloaidon,\n" +
            "CONCAT(DC.diachi, ' - ', DC.Xa, ' - ', DC.huyen, ' - ', DC.tinh, ' - ', DC.quocgia) as diachi,\n" +
            "HD.tennguoinhan, HD.sdtnguoinhan, TTHD.ghichu\n" +
            "FROM\n" +
            "hoadon HD JOIN loaidon LD ON HD.idloaidon = LD.id\n" +
            "JOIN trangthaihoadon TTHD ON TTHD.idhoadon = HD.id\n" +
            "LEFT JOIN taikhoan TKKH ON TKKH.id = HD.idkhachhang \n" +
            "LEFT JOIN diachi DC ON DC.idtaikhoan = TKKH.id\n" +
            "WHERE TTHD.idhoadon = ?1\n" +
            "ORDER BY TTHD.thoigian DESC", nativeQuery = true)
    ThongTinDonHang getThongTinDonHang(UUID idHoaDon);


    @Query(value = "SELECT IM.tenimage, SP.tensanpham, SP.giaban, HDCT.dongiasaugiam, HDCT.soluong FROM hoadon HD \n" +
            "LEFT JOIN hoadonchitiet HDCT ON HD.id = HDCT.idhoadon\n" +
            "LEFT JOIN sanphamchitiet SPCT ON SPCT.id = HDCT.idsanphamchitiet\n" +
            "LEFT JOIN sanpham SP ON SP.id = SPCT.idsanpham\n" +
            "LEFT JOIN spgiamgia SPGG ON SPGG.idsanpham = SP.id\n" +
            "LEFT JOIN image IM ON IM.idsanphamchitiet = SPCT.id WHERE IM.isdefault = 1 AND HD.id = ?1", nativeQuery = true)
    List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon);

    @Query(value = "SELECT HD.ma, HTTT.sotientra, HTTT.ngaytao, HTTT.phuongthucthanhtoan, HTTT.ghichu, TKNV.fullname FROM hoadon HD\n" +
            "LEFT JOIN hinhthucthanhtoan HTTT ON HD.id = HTTT.idhoadon\n" +
            "LEFT JOIN taikhoan TKNV ON HD.idnhanvien = TKNV.id WHERE HD.id = ?1\n", nativeQuery = true)
    List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon);



}
