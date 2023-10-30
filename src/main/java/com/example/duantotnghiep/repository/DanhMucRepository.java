package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.response.DanhMucResponse;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DanhMucRepository extends JpaRepository<DanhMuc, UUID> {
    @Query("select new com.example.duantotnghiep.response.DanhMucResponse(" +
            "dm.id, dm.tenDanhMuc)" +
            " from DanhMuc dm WHERE dm.trangThai = 1")
    Page<DanhMucResponse> getAllDoanhMuc(Pageable pageable);
}
