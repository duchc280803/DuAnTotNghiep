package com.example.duantotnghiep.controller.customer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/login")
    public String login() {
        return "/customer/login";
    }

    @GetMapping("/register")
    public String register() {
        return "/customer/register";
    }

    @GetMapping("/index")
    public String index() {
        return "/customer/index";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/customer/detail";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "/customer/checkout";
    }

    @GetMapping("/cart")
    public String cart() {
        return "/customer/cart";
    }

    @GetMapping("/shop")
    public String shop() {
        return "/customer/shop";
    }
}
