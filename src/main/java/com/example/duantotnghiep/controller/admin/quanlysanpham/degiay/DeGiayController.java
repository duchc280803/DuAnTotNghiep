package com.example.duantotnghiep.controller.admin.quanlysanpham.degiay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class DeGiayController {

    @GetMapping("/dashboard/deGiay")
    public String viewdeGiay() {
        return "/admin/deGiay/hien-thi";
    }

    @GetMapping("/dashboard/deGiay/create")
    public String createdeGiay() {
        return "/admin/deGiay/create";
    }

    @GetMapping("/dashboard/deGiay/update")
    public String updatedeGiay() {
        return "/admin/deGiay/update";
    }
}
