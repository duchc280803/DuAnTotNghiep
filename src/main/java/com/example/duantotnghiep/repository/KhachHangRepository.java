package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.response.KhachHangResponse;
import com.example.duantotnghiep.response.NhanVienOrderResponse;
import com.example.duantotnghiep.response.QLKhachHangResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<TaiKhoan, UUID> {

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, dc.diaChi, dc.xa, dc.huyen, dc.tinh) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.loaiTaiKhoan.name = 'USER' AND dc.trangThai = 1")
    List<KhachHangResponse> findlistKhachHang();

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, dc.diaChi, dc.xa, dc.huyen, dc.tinh) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.name = :key OR tk.soDienThoai = :key OR tk.email = :key AND dc.trangThai = 1")
    List<KhachHangResponse> findByKeyToKhachHang(@Param("key") String key);

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, dc.diaChi, dc.xa, dc.huyen, dc.tinh) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc JOIN tk.hoaDonKhachHangList hd WHERE hd.id = :id")
    KhachHangResponse findByKhachHangByIdHoaDon(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, dc.diaChi, dc.xa, dc.huyen, dc.tinh) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.id = :id AND dc.trangThai = 1")
    KhachHangResponse detailKhachHang(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.QLKhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, tk.image, tk.gioiTinh, tk.maTaiKhoan, tk.username, tk.matKhau, tk.ngaySinh, tk.trangThai, dc.tinh, dc.huyen, dc.xa) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.loaiTaiKhoan.name = ('USER') AND (:trangThai is null or tk.trangThai = :trangThai) AND (:name is null or tk.name like %:name%) AND (:soDienThoai is null or tk.soDienThoai like %:soDienThoai%) AND (:maTaiKhoan is null or tk.maTaiKhoan like %:maTaiKhoan%)")
    Page<QLKhachHangResponse> findlistQLKhachHang(@Param("trangThai") Integer trangThai, @Param("name") String name, @Param("soDienThoai") String soDienThoai, @Param("maTaiKhoan") String maTaiKhoan, Pageable pageable);

    @Query("SELECT new com.example.duantotnghiep.response.QLKhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, tk.image, tk.gioiTinh, tk.maTaiKhoan, tk.username, tk.matKhau, tk.ngaySinh, tk.trangThai, dc.tinh, dc.huyen, dc.xa) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.id = :id")
    QLKhachHangResponse detailQLKhachHang(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.QLKhachHangResponse(tk.id, tk.name, tk.email, tk.soDienThoai, tk.image, tk.gioiTinh, tk.maTaiKhoan, tk.username, tk.matKhau, tk.ngaySinh, tk.trangThai, dc.tinh, dc.huyen, dc.xa) " +
            "FROM TaiKhoan tk JOIN tk.diaChiList dc WHERE tk.maTaiKhoan = :key OR tk.soDienThoai = :key OR tk.name = :key OR tk.email = :key ")
    List<QLKhachHangResponse> findByKeyQLToKhachHang(@Param("key") String key);

    @Query("SELECT tk FROM TaiKhoan tk WHERE tk.loaiTaiKhoan.name = ('USER') ")
    List<TaiKhoan> listKhachHang();

    @Query(value = "select taikhoan.id, taikhoan.fullname, taikhoan.mataikhoan from taikhoan where idloaitaikhoan = '8FBBB1DF-1A27-4378-8498-D547FF4EC072'\n", nativeQuery = true)
    List<NhanVienOrderResponse> listNv();
}
