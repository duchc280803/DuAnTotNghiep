package com.example.duantotnghiep.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateKhachRequest {

    private String hoTen;

    private String soDienThoai;

    private String email;

    private String diaChi;

    private Date ngaySinh;
}
