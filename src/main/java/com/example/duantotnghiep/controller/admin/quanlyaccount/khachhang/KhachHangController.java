package com.example.duantotnghiep.controller.admin.quanlyaccount.khachhang;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class KhachHangController {

    @GetMapping("/dashboard/khach-hang")
    public String hienThi(){
        return "/admin/taikhoan/khachhang/khach-hang";
    }
}