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

    @GetMapping("/dashboard/voucher")
    public String viewVoucher(){
        return "/admin/voucher/hien-thi";
    }

    @GetMapping("/dashboard/create-voucher")
    public String viewCreateVoucher(){
        return "/admin/voucher/create";
    }
    //product
    @GetMapping("/dashboard/product")
    public String viewProduct(){
        return "/admin/product/hien-thi";
    }
    @GetMapping("/dashboard/product/create")
    public String createProduct(){
        return "/admin/product/create";
    }
    @GetMapping("/dashboard/product/update")
    public String updateProduct(){
        return "/admin/product/update";
    }
    //deGiay
    @GetMapping("/dashboard/deGiay")
    public String viewdeGiay(){
        return "/admin/deGiay/hien-thi";
    }
    @GetMapping("/dashboard/deGiay/create")
    public String createdeGiay(){
        return "/admin/deGiay/create";
    }
    @GetMapping("/dashboard/deGiay/update")
    public String updatedeGiay(){
        return "/admin/deGiay/update";
    }
    ////xuat xu
    @GetMapping("/dashboard/xuatXu")
    public String viewxuatXu(){
        return "/admin/xuatXu/hien-thi";
    }
    @GetMapping("/dashboard/xuatXu/create")
    public String createxuatXu(){
        return "/admin/xuatXu/create";
    }
    @GetMapping("/dashboard/xuatXu/update")
    public String updatexuatXu(){
        return "/admin/xuatXu/update";
    }
    //
////danh muc
    @GetMapping("/dashboard/danhMuc")
    public String viewdanhMuc(){
        return "/admin/danhMuc/hien-thi";
    }
    @GetMapping("/dashboard/danhMuc/create")
    public String createdanhMuc(){
        return "/admin/danhMuc/create";
    }
    @GetMapping("/dashboard/danhMuc/update")
    public String updatedanhMuc(){
        return "/admin/danhMuc/update";
    }
    //////chat lieu
    @GetMapping("/dashboard/chatLieu")
    public String viewchatLieu(){
        return "/admin/chatLieu/hien-thi";
    }
    @GetMapping("/dashboard/chatLieu/create")
    public String createchatLieu(){
        return "/admin/chatLieu/create";
    }
    @GetMapping("/dashboard/chatLieu/update")
    public String updatechatLieu(){
        return "/admin/chatLieu/update";
    }
    ////////thuong hieu
    @GetMapping("/dashboard/thuongHieu")
    public String viewthuongHieu(){
        return "/admin/thuongHieu/hien-thi";
    }
    @GetMapping("/dashboard/thuongHieu/create")
    public String createthuongHieu(){
        return "/admin/thuongHieu/create";
    }
    @GetMapping("/dashboard/thuongHieu/update")
    public String updatethuongHieu(){
        return "/admin/thuongHieu/update";
    }
//
}
