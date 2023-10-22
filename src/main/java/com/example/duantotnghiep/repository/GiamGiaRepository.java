package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface GiamGiaRepository extends JpaRepository<GiamGia, UUID> {
    @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.tenGiamGia,gg.maGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) " +
            "FROM GiamGia gg " +
            "JOIN gg.spGiamGiaList spgg"
        )
    List<GiamGiaResponse> listGiamGia();

    @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.tenGiamGia,gg.maGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) " +
            "FROM GiamGia gg " +
            "JOIN gg.spGiamGiaList spgg " +
            "WHERE gg.maGiamGia LIKE :key OR gg.tenGiamGia LIKE :key ")
   GiamGiaResponse findbyValueString(@Param("key") String key);

    @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.tenGiamGia,gg.maGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) " +
            "FROM GiamGia gg " +
            "JOIN gg.spGiamGiaList spgg " +
             "WHERE  gg.ngayBatDau >=  :key1 and  gg.ngayKetThuc <= :key2")
    List<GiamGiaResponse> findbyValueDate(@Param("key1") Date key1 ,@Param("key2") Date key2 );
    @Query("SELECT new com.example.duantotnghiep.response.GiamGiaResponse(gg.id,gg.tenGiamGia,gg.maGiamGia,gg.ngayBatDau,gg.ngayKetThuc,gg.hinhThucGiam,gg.trangThai,spgg.mucGiam) " +
            "FROM GiamGia gg " +
            "JOIN gg.spGiamGiaList spgg" +
            " JOIN spgg.sanPhamChiTiet spct " +
            "JOIN spct.sanPham sp " + "WHERE gg.trangThai = :key")
    List<GiamGiaResponse> findbyValueStatus(@Param("key") Integer key);
}
