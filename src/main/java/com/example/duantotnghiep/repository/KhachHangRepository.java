package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
import com.example.duantotnghiep.response.KhachHangResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<TaiKhoan, UUID> {
<<<<<<< HEAD

    @Query(value = "SELECT TK.id, TK.email, TK.image, TK.trangthai, TK.ten, TK.ngaysinh, TK.gioitinh, TK.sodienthoai "
=======
    @Query(value = "SELECT TK.id, TK.email, TK.image, TK.trangthai, TK.ten, TK.sodienthoai, TK.ngaysinh, TK.gioitinh "
>>>>>>> 8ad908c2030a56c77752838a166c22ab26456dbb
            + "FROM TaiKhoan TK "
            + "INNER JOIN VaiTro VT ON TK.idvaitro = VT.id "
            + "WHERE VT.tenvaitro = 'USER'",
            nativeQuery = true)
    List<KhachHangMap> findlistKhachHang();

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.name, kh.email, kh.soDienThoai) " +
            "FROM TaiKhoan kh JOIN kh.vaiTro vt WHERE vt.name = 'USER' AND kh.id= :id")
    KhachHangResponse findByKhachHang(@Param("id") UUID id);

    @Query("SELECT new com.example.duantotnghiep.response.KhachHangResponse(kh.id, kh.name, kh.email, kh.soDienThoai) " +
            "FROM TaiKhoan kh JOIN kh.vaiTro vt WHERE vt.name = 'USER' AND kh.name = :key OR kh.soDienThoai = :key OR kh.email = :key")
    KhachHangResponse findByKeyToKhachHang(@Param("key") String key);

}
