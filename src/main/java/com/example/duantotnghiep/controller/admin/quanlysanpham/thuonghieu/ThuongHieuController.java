package com.example.duantotnghiep.controller.admin.quanlysanpham.thuonghieu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class ThuongHieuController {

    @GetMapping("/dashboard/thuongHieu")
    public String viewthuongHieu() {
        return "/admin/thuongHieu/hien-thi";
    }

    @GetMapping("/dashboard/thuongHieu/create")
    public String createthuongHieu() {
        return "/admin/thuongHieu/create";
    }

    @GetMapping("/dashboard/thuongHieu/update")
    public String updatethuongHieu() {
        return "/admin/thuongHieu/update";
    }
}
