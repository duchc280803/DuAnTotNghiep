package com.example.duantotnghiep.response;

import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface MoneyResponse {

    BigDecimal getThanhTien();

    BigDecimal getTienShip();

    BigDecimal getTienGiamGia();

    BigDecimal getTienKhachTra();

    BigDecimal getTienThua();

    BigDecimal getTongTien();



}
