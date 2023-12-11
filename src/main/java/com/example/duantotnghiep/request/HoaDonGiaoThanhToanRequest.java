package com.example.duantotnghiep.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoaDonGiaoThanhToanRequest {

    private UUID idHoaDon;

    @NotBlank(message = "Họ và tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String soDienThoai;

    @NotBlank(message = "Tỉnh không được để trống")
    private String tinh;

    @NotBlank(message = "Huyện không được để trống")
    private String huyen;

    @NotBlank(message = "Phường không được để trống")
    private String phuong;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotBlank(message = "Email không được để trống")
    private String email;

    @NotNull(message = "Tiền vận chuyển không được để trống")
    private BigDecimal tienGiao;

    private BigDecimal tongTien;

    private BigDecimal tienKhachTra;

    private BigDecimal tienThua;

    private List<UUID> gioHangChiTietList;
}
