package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.HinhThucThanhToan;
import com.example.duantotnghiep.response.HinhThucThanhToanResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HinhThucThanhToanRepository extends JpaRepository<HinhThucThanhToan, UUID> {


}
