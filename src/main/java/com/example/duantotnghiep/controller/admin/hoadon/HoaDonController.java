package com.example.duantotnghiep.controller.admin.hoadon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class HoaDonController {
    @GetMapping("/dashboard/hoa-don")
    public String hoadon(){
        return "/admin/hoaDon/hoa-don";
    }
}
