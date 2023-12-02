package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ConfirmOrderClientRequest {

    private BigDecimal tienShip;

    private String diaChi;

    private String hoVatenClient;

    private String email;

    private String sdtClient;
}
