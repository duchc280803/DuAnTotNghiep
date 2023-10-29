package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.mapper.SoLuongGioHangCustom;
import com.example.duantotnghiep.mapper.TongTienCustom;
import com.example.duantotnghiep.response.SanPhamResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {
    //lấy ra số lượng trên giỏ hàng
    @Query(value = "SELECT SUM(ghct.soluong) AS soluong\n" +
            "FROM giohangchitiet ghct\n" +
            "JOIN giohang gh ON gh.id = ghct.idgiohang\n" +
            "WHERE gh.id = ?1 AND ghct.soluong > 0;",nativeQuery = true)
    List<SoLuongGioHangCustom> getSoLuongGioHang(UUID idgh);

    //Tính tổng tiền tất cả sản phẩm trên giỏ hàng
    @Query(value = "SELECT SUM(sct.giaban * ghct.soluong) AS tongtien\n" +
            "FROM sanphamchitiet sct\n" +
            "JOIN sanpham sp ON sct.idsanpham = sp.id\n" +
            "JOIN size s ON sct.idsize = s.id\n" +
            "JOIN image img ON sct.id = img.idsanphamchitiet\n" +
            "JOIN giohangchitiet ghct ON ghct.idsanphamchitiet = sct.id\n" +
            "JOIN giohang gh ON gh.id = ghct.idgiohang\n" +
            "JOIN mausac ms ON ms.id = sct.idmausac\n" +
            "WHERE img.isdefault = 'true' AND gh.id = ?1 AND ghct.soluong > 0;",nativeQuery = true)
    List<TongTienCustom> getTongTien(UUID idgh);

    @Query(value = "SELECT TOP 8 sp.id, sp.tensanpham, im.tenimage, sp.giaban FROM sanpham sp JOIN sanphamchitiet spct ON sp.id = spct.idsanpham JOIN \n" +
            "image im ON spct.id = im.idsanphamchitiet ORDER BY sp.ngaytao DESC", nativeQuery = true)
    List<SanPhamResponse> getNewProduct();








}
