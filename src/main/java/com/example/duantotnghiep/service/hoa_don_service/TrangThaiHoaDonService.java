package com.example.duantotnghiep.service.hoa_don_service;

import java.util.UUID;

public interface TrangThaiHoaDonService {
    void updateTrangThaiHoaDon(UUID idHoaDon, Integer trangThai, String ghiChu);
}
