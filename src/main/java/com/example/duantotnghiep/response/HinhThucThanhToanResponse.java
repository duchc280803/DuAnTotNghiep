package com.example.duantotnghiep.response;

import java.math.BigDecimal;
import java.util.Date;

public interface HinhThucThanhToanResponse {
    String getMa();

    BigDecimal getSoTienTra();

    Date getNgayTao();

    Integer getPhuongThucThanhToan();

    String getGhiChu();

    String getFullName();
}
