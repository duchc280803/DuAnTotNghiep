package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {

    private String qrcode;

    private Integer soLuong;

    private UUID idChatLieu;

    private UUID idSanPham;

    private UUID idSize;

    private UUID idMauSac;

}
