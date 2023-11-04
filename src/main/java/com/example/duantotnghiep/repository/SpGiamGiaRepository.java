package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.mapper.not_login.findIdSpctAndSoLuong_not_login;
import com.example.duantotnghiep.mapper.not_login.loadmausac_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsanpham_not_login;
import com.example.duantotnghiep.mapper.not_login.loadsize_not_login;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.SanPhamGiamGiaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpGiamGiaRepository extends JpaRepository<SpGiamGia, UUID> {

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

    @Query(value = "SELECT sp.id, i.tenImage, sp.tenSanPham, sp.giaBan, spgg.donGiaKhiGiam, spgg.mucGiam\n" +
            "            FROM SanPham sp\n" +
            "            LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "            LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "            JOIN Image i ON sp.id = i.idsanpham\n" +
            "            JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
            "            JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
            "            JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
            "            JOIN kieude kd ON kd.id = sp.idkieude\n" +
            "            WHERE i.isDefault = 'true' AND sp.id='E4592D5B-6941-4FE0-B067-3E4054207EA8'\n" +
            "\t\t\tAND (\n" +
            "    spgg.id IS NULL -- Nếu không có giảm giá nào, hoặc\n" +
            "    OR (\n" +
            "        spgg.id IS NOT NULL\n" +
            "        AND spgg.id = (\n" +
            "            SELECT MIN(spgg_inner.id)\n" +
            "            FROM spgiamgia spgg_inner\n" +
            "            WHERE spgg_inner.idsanpham = sp.id\n" +
            "        )\n" +
            "    )\n" +
            ");\n",nativeQuery = true)
    List<loadsanpham_not_login> getNamePriceImageByIdSanPham(UUID idsanpham);

    //load all mau sac
    @Query(value = "SELECT DISTINCT ms.id, ms.tenmausac\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "WHERE sp.id = ?",nativeQuery = true)
    List<loadmausac_not_login> getAllMauSac(UUID idsanpham);

    //load all size
    @Query(value = "SELECT DISTINCT s.id, s.size\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN size s ON spct.idsize = s.id\n" +
            "WHERE sp.id = ?",nativeQuery = true)
    List<loadsize_not_login> getAllSize(UUID idsanpham);

    //find size by mau sac
    @Query(value = "SELECT DISTINCT s.id, s.size\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN size s ON spct.idsize = s.id\n" +
            "JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "WHERE sp.id = ?1\n" +
            "AND ms.id = ?2",nativeQuery = true)
    List<loadsize_not_login> findSizeByMauSac(UUID idsanpham,UUID idmausac);

    //find mau sac by size
    @Query(value = "SELECT DISTINCT ms.id, ms.tenmausac\n" +
            "FROM sanpham sp\n" +
            "JOIN sanphamchitiet spct ON sp.id = spct.idsanpham\n" +
            "JOIN size s ON spct.idsize = s.id\n" +
            "JOIN mausac ms ON spct.idmausac = ms.id\n" +
            "WHERE sp.id = ?1\n" +
            "AND s.id = ?2",nativeQuery = true)
    List<loadmausac_not_login> findMauSacBySize(UUID idsanpham,UUID idsize);

    //load sp chi tiet
    @Query(value = "SELECT spct.id,soluong FROM SanPham sp\n" +
            "            LEFT JOIN spgiamgia spgg ON sp.id = spgg.idsanpham\n" +
            "            LEFT JOIN GiamGia gg ON spgg.idgiamgia = gg.id\n" +
            "            JOIN Image i ON sp.id = i.idsanpham\n" +
            "            JOIN ThuongHieu th ON sp.idthuonghieu = th.id\n" +
            "            JOIN DanhMuc dm ON sp.iddanhmuc = dm.id\n" +
            "            JOIN XuatXu xx ON sp.idxuatxu = xx.id\n" +
            "            JOIN sanphamchitiet spct on spct.idsanpham = sp.id\n" +
            "            JOIN chatlieu cl on spct.idchatlieu = cl.id\n" +
            "            JOIN mausac ms on spct.idmausac = ms.id\n" +
            "            JOIN kieude kd on sp.idkieude = kd.id\n" +
            "            JOIN size s on spct.idsize = s.id\n" +
            "            WHERE i.isDefault = 'true' and ms.id=?1 and s.id=?2\n" +
            "            and sp.id=?3",nativeQuery = true)
    List<findIdSpctAndSoLuong_not_login> findIdspctAndSoluong(UUID idmausac, UUID idsize, UUID idsanpham);

    List<SpGiamGia> findBySanPham_Id(UUID id);
}
