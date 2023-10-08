package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.SanPhamGetAllSizeCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChiTietSanPhamRepository extends JpaRepository<SanPhamChiTiet, UUID> {
    //load sản phẩm với 1 ảnh mặc định
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' ", nativeQuery = true)
    List<ChiTietSanPhamCustom> getAll();

    //tìm kiếm sản phẩm theo tên
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true'  AND SP.TenSanPham LIKE %?1%", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByName(String name);

    //Chọn ra 1 sản phẩm bao gồm tất cả các size
    @Query(value = "SELECT sp.TenSanPham,img.TenImage,th.TenThuongHieu,dm.TenDanhMuc,xs.TenXuatXu,sct.GiaBan,s.Size,sct.SoLuong FROM SanPhamChiTiet sct\n" +
            "            JOIN Size s on sct.IdSize = s.Id\n" +
            "            JOIN SanPham sp on sct.IdSanPham=sp.Id\n" +
            "            JOIN Image img on sct.Id= img.idsanphamchitiet\n" +
            "            JOIN XuatXu xs on sp.IdXuatXu = xs.Id\n" +
            "            JOIN ThuongHieu th on sp.IdThuongHieu = th.Id\n" +
            "            JOIN DanhMuc dm on sp.IdDanhMuc = dm.Id\n" +
            "            where img.isDefault = 'true'\n" +
            "            AND sp.id = ?1", nativeQuery = true)
    List<SanPhamGetAllSizeCustom> getByIdSp(UUID id);
    //--------------------Lọc sản phẩm theo các thuộc tính------------------

    //1.tìm kiếm sản phẩm theo idDanhMuc
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND DM.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdDanhMuc(UUID idDanhMuc);

    //2.tìm kiếm sản phẩm theo idXuatSu
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND XS.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdXuatSu(UUID idXuatSu);

    //3.tìm kiếm sản phẩm theo idThuongHieu
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND TH.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdThuongHieu(UUID idThuongHieu);

    //4.tìm kiếm sản phẩm theo idKieuDe
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND KD.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdKieuDe(UUID idKieuDe);

    //5.tìm kiếm sản phẩm theo idChatLieu
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND CL.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdChatLieu(UUID idChatLieu);

    //6.tìm kiếm sản phẩm theo idMauSac
    @Query(value = "SELECT IMG.TenImage,SCT.Id,SP.TenSanPham,SCT.GiaBan\n" +
            "            FROM SanPhamChiTiet SCT \n" +
            "            JOIN SanPham SP ON SCT.IdSanPham = SP.Id\n" +
            "            JOIN DanhMuc DM ON SP.IdDanhMuc = DM.Id \n" +
            "            JOIN XuatXu XS ON SP.IdXuatXu = XS.Id\n" +
            "            JOIN Image IMG ON IMG.idsanphamchitiet = SCT.Id\n" +
            "            JOIN ThuongHieu TH ON SP.IdThuongHieu = TH.Id\n" +
            "            JOIN ChatLieu CL ON SCT.IdChatLieu = CL.Id\n" +
            "            JOIN Size S ON SCT.IdSize = S.Id\n" +
            "            JOIN MauSac MS ON SCT.IdMauSac = MS.Id       \n" +
            "            WHERE IMG.isDefault = 'true' AND MS.Id = ?1", nativeQuery = true)
    List<ChiTietSanPhamCustom> searchByIdMauSac(UUID idMauSac);

}
