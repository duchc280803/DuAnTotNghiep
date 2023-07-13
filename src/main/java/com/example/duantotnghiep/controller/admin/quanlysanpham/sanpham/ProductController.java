package com.example.duantotnghiep.controller.admin.quanlysanpham.sanpham;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class ProductController {

    @GetMapping("/dashboard/product")
    public String viewProduct() {
        return "/admin/product/hien-thi";
    }

    @GetMapping("/dashboard/product/create")
    public String createProduct() {
        return "/admin/product/create";
    }

    @GetMapping("/dashboard/product/update")
    public String updateProduct() {
        return "/admin/product/update";
    }
}
