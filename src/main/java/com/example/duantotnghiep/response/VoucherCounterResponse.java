package com.example.duantotnghiep.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherCounterResponse {

    private String voucherCode;

    private String nameVoucher;

    private BigDecimal price;

    private BigDecimal priceOrder;

    private Date dateStart;

    private Date dateEnd;
}
