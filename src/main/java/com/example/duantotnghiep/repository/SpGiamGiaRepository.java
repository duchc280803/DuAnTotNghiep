package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.mapper.not_login.loadmausac_chatlieu_not_login;
import com.example.duantotnghiep.mapper.not_login.*;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpGiamGiaRepository extends JpaRepository<SpGiamGia, UUID> {

    //load sanpham on shop
    @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam\n" +
            "FROM SanPham sp\n" +
            "LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "JOIN Image i ON sp.id = i.idsanpham\n" +
            "JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
            "JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
            "JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
            "JOIN kieude kd ON kd.id = sp.idkieude\n" +
            "WHERE i.isDefault = 'true'\n" +
            "AND (\n" +
            "    spgg.id IS NULL \n" +
            "    OR (\n" +
            "        spgg.id IS NOT NULL\n" +
            "        AND spgg.id = (\n" +
            "            SELECT MIN(spgg_inner.id)\n" +
            "            FROM spgiamgia spgg_inner\n" +
            "            WHERE spgg_inner.idsanpham = sp.id\n" +
            "        )\n" +
            "    )\n" +
            ")",nativeQuery = true)
    List<loadsanpham_not_login> getAllSpGiamGia();

    //load thong tin san pham detail
    @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam\n" +
            "                        FROM SanPham sp\n" +
            "                        LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "                        LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "                        JOIN Image i ON sp.id = i.idsanpham\n" +
            "                        JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
            "                        JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
            "                        JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
            "                        JOIN kieude kd ON kd.id = sp.idkieude\n" +
            "                        WHERE i.isDefault = 'true' AND sp.tensanpham=?\n" +
            "            AND (\n" +
            "                spgg.id IS NULL \n" +
            "                OR (\n" +
            "                    spgg.id IS NOT NULL\n" +
            "                    AND spgg.id = (\n" +
            "                        SELECT MIN(spgg_inner.id)\n" +
            "                        FROM spgiamgia spgg_inner\n" +
            "                        WHERE spgg_inner.idsanpham = sp.id\n" +
            "                    )\n" +
            "                )\n" +
            "            )",nativeQuery = true)
    loadsanpham_not_login getNamePriceImageByIdSanPham(String tenSanPham);

    //load all mau sac
    @Query(value = "SELECT DISTINCT ms.id, ms.tenmausac\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "WHERE sp.tensanpham = ?",nativeQuery = true)
    List<loadmausac_not_login> getAllMauSac(String tensanpham);

    //load all size
    @Query(value = "SELECT DISTINCT s.id, s.size\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN size s ON spct.idsize = s.id\n" +
            "WHERE sp.tensanpham = ?",nativeQuery = true)
    List<loadsize_not_login> getAllSize(String tensanpham);

    //load all chatlieu
    @Query(value = "SELECT DISTINCT cl.id, cl.tenchatlieu\n" +
            "            FROM sanpham sp\n" +
            "            JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "            JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "            WHERE sp.tensanpham = ?",nativeQuery = true)
    List<loadchatlieu_not_login> getAllChatLieu(String tensanpham);

    //find by //SIZE// load  //MAUSAC + CHATLIEU//
    @Query(value = "SELECT DISTINCT spct.idMauSac,ms.tenMauSac , spct.idChatLieu ,cl.tenChatLieu\n" +
            "        FROM sanpham sp\n" +
            "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "        JOIN size s ON spct.idsize = s.id\n" +
            "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "        WHERE sp.tensanpham = ?1\n" +
            "        AND s.id = ?2",nativeQuery = true)
    List<loadmausac_chatlieu_not_login> findMauSacChatLieu(String tensanpham,UUID idsize);

    //find by //MAUSAC// load  //SIZE + CHATLIEU//
    @Query(value = "\t\tSELECT DISTINCT spct.idsize,s.size , spct.idchatlieu ,cl.tenchatlieu\n" +
            "        FROM sanpham sp\n" +
            "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "        JOIN size s ON spct.idsize = s.id\n" +
            "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "        WHERE sp.tensanpham = :tensanpham\n" +
            "        AND ms.id = :idmausac",nativeQuery = true)
    List<loadsize_chatlieu_not_login> findSizeChatLieu(@Param("tensanpham") String tensanpham,@Param("idmausac") UUID idmausac);

    //find by //CHATLIEU// load  //SIZE + MAUSAC//
    @Query(value = "SELECT DISTINCT  spct.idsize,s.size, spct.idmausac,ms.tenmausac\n" +
            "        FROM sanpham sp\n" +
            "        JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "        JOIN size s ON spct.idsize = s.id\n" +
            "        JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "        JOIN chatlieu cl ON spct.idchatlieu = cl.id\n" +
            "        WHERE sp.tensanpham = :tensanpham\n" +
            "        AND cl.id = :idchatlieu",nativeQuery = true)
    List<loadmausac_size_not_login> findSizeMauSac(@Param("tensanpham") String tensanpham,@Param("idchatlieu") UUID idchatlieu);

    //load sp chi tiet
    @Query(value = "SELECT spct.id,soluong FROM SanPham sp\n" +
            "                        LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "                        LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "                        JOIN Image i ON sp.id = i.idsanpham\n" +
            "                        JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
            "                        JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
            "                       JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
            "                       JOIN sanphamchitiet spct on spct.idsanpham = sp.id\n" +
            "                        JOIN chatlieu cl on spct.idchatlieu = cl.id\n" +
            "                        JOIN mausac ms on spct.idmausac = ms.id\n" +
            "                        JOIN kieude kd on sp.idkieude = kd.id\n" +
            "                        JOIN size s on spct.idsize = s.id\n" +
            "                        WHERE i.isDefault = 'true' and ms.id= :idmausac and s.id= :idsize and cl.id = :idchatlieu \n" +
            "                        and sp.tensanpham = :tensanpham\n" +
            "\t\t\t\t\t\tand cl.id =spct.idchatlieu\n" +
            "\t\t\t\t\t\tAND (\n" +
            "                            spgg.id IS NULL\n" +
            "                            OR (\n" +
            "                                spgg.id IS NOT NULL\n" +
            "                                AND spgg.id = (\n" +
            "                                    SELECT MIN(spgg_inner.id)\n" +
            "                                   FROM spgiamgia spgg_inner\n" +
            "                                   WHERE spgg_inner.idsanpham = sp.id\n" +
            "                                )\n" +
            "                            )\n" +
            "                        )",nativeQuery = true)
    findIdSpctAndSoLuong_not_login findIdspctAndSoluong(@Param("idmausac") UUID idmausac,@Param("idsize") UUID idsize,@Param("idchatlieu") UUID idchatlieu, @Param("tensanpham") String tensanpham);

    List<SpGiamGia> findBySanPham_Id(UUID id);
}
