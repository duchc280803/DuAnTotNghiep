package com.example.duantotnghiep.controller.admin.quanlysanpham.xuatxu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class XuatXuController {

    @GetMapping("/dashboard/xuatXu")
    public String viewxuatXu() {
        return "/admin/xuatXu/hien-thi";
    }

    @GetMapping("/dashboard/xuatXu/create")
    public String createxuatXu() {
        return "/admin/xuatXu/create";
    }

    @GetMapping("/dashboard/xuatXu/update")
    public String updatexuatXu() {
        return "/admin/xuatXu/update";
    }
}
