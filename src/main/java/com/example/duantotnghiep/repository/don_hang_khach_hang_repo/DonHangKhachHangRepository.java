package com.example.duantotnghiep.repository.don_hang_khach_hang_repo;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.mapper.don_hang_khach_hang.DonHangKhachHangMap;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.OrderCounterCartsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DonHangKhachHangRepository extends JpaRepository<HoaDon, UUID> {
    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = :trangthai  AND hdct.trangthai = :trangthai AND hd.trangthai = :trangthai)\n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1",nativeQuery = true)
    List<DonHangKhachHangMap> filterStatus(String username,Integer trangthai);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = :trangthai  AND hdct.trangthai = :trangthai AND hd.trangthai = :trangthai) \n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n" +
            "                        AND (tensanpham LIKE %:tensanpham% OR mahoadon LIKE %:mahoadon%)\n",nativeQuery = true)
    List<DonHangKhachHangMap> searchByMaOrName(@Param("username") String username,@Param("trangthai") String trangthai, @Param("tensanpham") String tensanpham, @Param("mahoadon") String mahoadon);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 5  AND hdct.trangthai = 7\n" +
            "                        )\n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n",nativeQuery = true)
    List<DonHangKhachHangMap> loadTraHang(String username);

    @Query(value = "WITH CTE AS (SELECT hd.id AS idhoadon,hd.ma AS mahoadon,sp.id AS idsanpham,sp.tensanpham,img.tenimage,s.size,cl.tenchatlieu,ms.tenmausac,hdct.soluong,hdct.dongia,hdct.dongiasaugiam,hd.thanhtien as thanhtien,\n" +
            "                            SUM(hdct.soluong) OVER (PARTITION BY hd.id) AS tongsoluong,\n" +
            "                            ROW_NUMBER() OVER (PARTITION BY hd.id ORDER BY hdct.id) AS RowNum\n" +
            "                            FROM hoadon hd JOIN hoadonchitiet hdct ON hd.id = hdct.idhoadon JOIN sanphamchitiet spct ON hdct.idsanphamchitiet = spct.id JOIN sanpham sp ON spct.idsanpham = sp.id\n" +
            "                            JOIN danhmuc dm ON sp.iddanhmuc = dm.id JOIN thuonghieu th ON sp.idthuonghieu = th.id JOIN xuatxu xx ON sp.idxuatxu = xx.id JOIN image img ON sp.id = img.idsanpham\n" +
            "                            JOIN kieude kd ON sp.idkieude = kd.id JOIN size s ON spct.idsize = s.id JOIN mausac ms ON spct.idmausac = ms.id JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "                            JOIN trangthaihoadon tthd ON tthd.idhoadon = hd.id JOIN taikhoan tk ON tk.id = hd.idkhachhang\n" +
            "                            WHERE img.isdefault = 'true' AND tk.username = :username AND tthd.trangthai = 5  AND hdct.trangthai = 7) \n" +
            "                        SELECT idhoadon,mahoadon,idsanpham,tensanpham,tenimage,size,tenchatlieu,tenmausac,soluong,dongia,dongiasaugiam,thanhtien,tongsoluong FROM CTE WHERE RowNum = 1\n" +
            "                        AND (tensanpham LIKE %:tensanpham% OR mahoadon LIKE %:mahoadon%)\n",nativeQuery = true)
    List<DonHangKhachHangMap> searchByMaOrNameTraHang(@Param("username") String username, @Param("tensanpham") String tensanpham, @Param("mahoadon") String mahoadon);
}
