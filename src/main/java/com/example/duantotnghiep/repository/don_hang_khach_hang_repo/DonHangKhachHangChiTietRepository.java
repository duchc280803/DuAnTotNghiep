package com.example.duantotnghiep.repository.don_hang_khach_hang_repo;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.ThongTinDonHangKhachHangMap;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.ThongTinSanPhamKhachHangMap;
import com.example.duantotnghiep.response.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonHangKhachHangChiTietRepository extends JpaRepository<HoaDonChiTiet, UUID> {
    @Query(value = "SELECT TOP 1 HD.ma,HD.trangthai,\n" +
            "            HD.diachi,\n" +
            "            HD.tennguoinhan, HD.sdtnguoinhan,  HD.ngayship, HD.sdtnguoiship, TTHD.ghichu\n" +
            "            FROM hoadon HD JOIN loaidon LD ON HD.idloaidon = LD.id\n" +
            "            JOIN trangthaihoadon TTHD ON TTHD.idhoadon = HD.id\n" +
            "\t\t\tJOIN hinhthucthanhtoan httt ON httt.idhoadon = HD.id\n" +
            "           JOIN taikhoan tk on tk.id = hd.idkhachhang  " +
            "WHERE TTHD.idhoadon = :idHoaDon and tk.username = :username \n" +
            "ORDER BY TTHD.thoigian DESC\n" +
            "\t\t\t\t\n" +
            "\t\t\n" +
            "\n", nativeQuery = true)
    ThongTinDonHangKhachHangMap getThongTinDonHang(@Param("idHoaDon") UUID idHoaDon, @Param("username") String username);

    @Query(value = "SELECT hdct.id AS id,i.tenImage AS tenImage,sp.tenSanPham AS tenSanPham,hdct.donGia AS donGia,hdct.donGiaSauGiam AS donGiaSauGiam,hdct.soLuong AS soLuong,s.size,ms.tenmausac,cl.tenchatlieu\n" +
            "FROM HoaDon hd\n" +
            "JOIN hoaDonChiTiet hdct ON hd.id = hdct.idHoaDon\n" +
            "JOIN sanPhamChiTiet spct ON hdct.idSanPhamChiTiet = spct.id\n" +
            "JOIN sanPham sp ON spct.idSanPham = sp.id\n" +
            "JOIN image i ON sp.id = i.idSanPham\n" +
            "JOIN Size s ON spct.idsize = s.id\n" +
            "JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "JOIN mausac ms ON spct.idmausac = ms.id \n" +
            "JOIN taikhoan tk on tk.id = hd.idkhachhang\n" +
            "WHERE i.isDefault = 1 AND hd.id = :idHoaDon and tk.username = :username \n",nativeQuery = true)
    List<ThongTinSanPhamKhachHangMap> getSanPhamHDCT(@Param("idHoaDon") UUID idHoaDon,@Param("username") String username);

    @Query("SELECT new com.example.duantotnghiep.response.MoneyResponse(hd.thanhTien, hd.tienShip, hd.tienThua, hd.tienGiamGia)" +
            "FROM HoaDon hd WHERE hd.id = :idHoaDon")
    MoneyResponse getAllMoneyByHoaDon(UUID idHoaDon);

    @Query("SELECT new com.example.duantotnghiep.response.TrangThaiHoaDonResponse(tthd.trangThai, tthd.thoiGian, tknv.name, tthd.ghiChu) " +
            "FROM HoaDon hd " +
            "JOIN hd.trangThaiHoaDonList tthd " +
            "LEFT JOIN hd.taiKhoanNhanVien tknv WHERE hd.id = :id ORDER BY tthd.thoiGian ASC ")
    List<TrangThaiHoaDonResponse> getAllTrangThaiHoaDon(@Param("id") UUID id);

}
