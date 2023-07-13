package com.example.duantotnghiep.controller.admin.quanlysanpham.chatlieu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class ChatLieuController {

    @GetMapping("/dashboard/chatLieu")
    public String viewchatLieu() {
        return "/admin/chatLieu/hien-thi";
    }

    @GetMapping("/dashboard/chatLieu/create")
    public String createchatLieu() {
        return "/admin/chatLieu/create";
    }

    @GetMapping("/dashboard/chatLieu/update")
    public String updatechatLieu() {
        return "/admin/chatLieu/update";
    }
}
