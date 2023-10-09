package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.mapper.KhachHangMap;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface KhachHangRepository extends JpaRepository<TaiKhoan, UUID> {
    @Query(value = "SELECT TK.id, TK.email, TK.image, TK.trangthai, TK.ten, TK.sodienthoai, TK.ngaysinh, TK.gioitinh "
            + "FROM TaiKhoan TK "
            + "INNER JOIN VaiTro VT ON TK.idvaitro = VT.id "
            + "WHERE VT.tenvaitro = 'USER'",
            nativeQuery = true)
    List<KhachHangMap> findlistKhachHang();

}
