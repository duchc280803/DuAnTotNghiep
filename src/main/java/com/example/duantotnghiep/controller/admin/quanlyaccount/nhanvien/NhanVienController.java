package com.example.duantotnghiep.controller.admin.quanlyaccount.nhanvien;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class NhanVienController {

    @GetMapping("/dashboard/nhan-vien")
    public String hienThi(){
        return "/admin/taikhoan/nhanvien/nhan-vien";
    }
}
