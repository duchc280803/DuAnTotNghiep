package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.enums.RoleEnum;
import com.example.duantotnghiep.response.HoaDonResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, UUID> {

    @Query("SELECT NEW com.example.duantotnghiep.response.HoaDonResponse(hd.id, hd.ma, tk.name, hd.trangThai)" +
            " FROM HoaDon hd JOIN hd.taiKhoanNhanVien tk WHERE hd.trangThai = 1")
    List<HoaDonResponse> viewHoaDonTaiQuay();

}
