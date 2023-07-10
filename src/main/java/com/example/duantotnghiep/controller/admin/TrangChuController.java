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
    public String viewBanHang(){
        return "/admin/oder/hien-thi";
    }

    @GetMapping("/dashboard/don-hang")
    public String donHang(){
        return "/admin/oder/tao-don-hang";
    }

    @GetMapping("/dashboard/voucher")
    public String viewVoucher(){
        return "/admin/voucher/hien-thi";
    }

    @GetMapping("/dashboard/create-voucher")
    public String viewCreateVoucher(){
        return "/admin/voucher/create";
    }

}
