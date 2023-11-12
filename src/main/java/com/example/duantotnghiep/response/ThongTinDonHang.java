package com.example.duantotnghiep.response;

import java.util.UUID;

public interface ThongTinDonHang {
    Integer getTrangThai();

    String getTenLoaiDon();

    String getDiaChi();

    String getTenNguoiNhan();

    String getSdtNguoiNhan();

    String getTenNguoiShp();

    String getSdtNguoiShip();

    String getGhiChu();

    UUID getIdNhanVien();

}
