package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GiamGiaRequest {




    private String tenGiamGia;


    private String maGiamGia;

    private Date ngayBatDau;

    private Date ngayKetThuc;

    private Integer hinhThucGiam;

    private Integer trangThai;
    private Long mucGiam;
    private  UUID idsanpham;
    private  UUID idchatLieu;
    private  UUID idmausac;
    private  UUID idkieude;
    private  UUID idsize;
//  private  String image;

}
