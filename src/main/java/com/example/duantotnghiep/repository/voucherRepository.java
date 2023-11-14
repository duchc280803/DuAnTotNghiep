package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface voucherRepository extends JpaRepository<Voucher, UUID> {
    Optional<Voucher> findById(UUID id);

    // Thêm method tìm kiếm theo tên hoặc mã voucher
    List<Voucher> findByTenVoucherContainingIgnoreCaseOrMaVoucherContainingIgnoreCase(String tenVoucher,
            String maVoucher);
}
