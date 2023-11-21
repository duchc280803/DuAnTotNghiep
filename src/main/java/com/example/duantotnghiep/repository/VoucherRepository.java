package com.example.duantotnghiep.repository;

import com.example.duantotnghiep.entity.Voucher;
import com.example.duantotnghiep.response.VoucherCounterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoucherRepository extends JpaRepository<Voucher, UUID> {
    Optional<Voucher> findById(UUID id);

    List<Voucher> findByTenVoucherContainingIgnoreCaseOrMaVoucherContainingIgnoreCase(String tenVoucher,
            String maVoucher);

    @Query("SELECT new com.example.duantotnghiep.response.VoucherCounterResponse" +
            "(v.id, v.maVoucher, v.tenVoucher, v.hinhThucGiam, v.soLuongMa, v.soLuongDung,v.giaTriGiam, v.giaTriToiThieuDonhang, v.ngayBatDau, v.ngayKetThuc) FROM Voucher v ")
    Page<VoucherCounterResponse> findAllVoucher(Pageable pageable);

    List<Voucher> findByTrangThai(Integer trangThai);
}
