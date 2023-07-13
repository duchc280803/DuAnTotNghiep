package com.example.duantotnghiep.controller.admin.bantaiquay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/")
public class BanTaiQuayController {

    @GetMapping("/dashboard/don-hang")
    public String don(){
        return "/admin/oder/don-hang";
    }

    @GetMapping("don-hang/1")
    public String chitietdonhang(){
        return "/admin/oder/chi-tiet-don-hang";
    }

    @GetMapping("/dashboard/tao-don-hang")
    public String donHang(){
        return "/admin/oder/tao-don-hang";
    }

}
