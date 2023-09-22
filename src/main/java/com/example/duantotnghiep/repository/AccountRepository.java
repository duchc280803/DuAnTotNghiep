package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.response.NhanVienResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<TaiKhoan, UUID> {

    @Query("select new com.example.duantotnghiep.response.NhanVienResponse(" +
            "tk.image,tk.username,tk.email,tk.name,tk.trangThai)" +
            " from TaiKhoan tk JOIN tk.vaiTro vt where vt.name = 'EMPLOYEE'")
    Page<NhanVienResponse> getAllPage(Pageable pageable);

    Optional<TaiKhoan> findByUsername(String username);
}
