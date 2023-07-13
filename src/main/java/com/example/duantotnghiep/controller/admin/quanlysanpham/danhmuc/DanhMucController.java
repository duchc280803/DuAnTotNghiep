package com.example.duantotnghiep.controller.admin.quanlysanpham.danhmuc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class DanhMucController {

    @GetMapping("/dashboard/danhMuc")
    public String viewdanhMuc() {
        return "/admin/danhMuc/hien-thi";
    }

    @GetMapping("/dashboard/danhMuc/create")
    public String createdanhMuc() {
        return "/admin/danhMuc/create";
    }

    @GetMapping("/dashboard/danhMuc/update")
    public String updatedanhMuc() {
        return "/admin/danhMuc/update";
    }
}
