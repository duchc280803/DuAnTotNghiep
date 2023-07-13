package com.example.duantotnghiep.controller.admin.quanlyvoucher;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class VoucherController {

    @GetMapping("/dashboard/voucher")
    public String viewVoucher(){
        return "/admin/voucher/hien-thi";
    }

    @GetMapping("/dashboard/create-voucher")
    public String viewCreateVoucher(){
        return "/admin/voucher/create";
    }
}
