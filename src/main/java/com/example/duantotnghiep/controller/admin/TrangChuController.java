package com.example.duantotnghiep.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class TrangChuController {

    @GetMapping("dashboard")
    public String dashboard(){
        return "/admin/trang-chu";
    }

    @GetMapping("/dashboard/cart")
    public String banHangTaiQuay(){
        return "/admin/ban-hang-tai-quay";
    }

}
