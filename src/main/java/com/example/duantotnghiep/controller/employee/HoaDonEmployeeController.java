package com.example.duantotnghiep.controller.employee;

import com.example.duantotnghiep.response.HoaDonCustomResponse;
import com.example.duantotnghiep.service.impl.HoaDonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user/hoa-don/")
public class HoaDonEmployeeController {

    @Autowired
    private HoaDonServiceImpl hoaDonService;

    @GetMapping("hien-thi")
    public ResponseEntity<List<HoaDonCustomResponse>> getAll(
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        return new ResponseEntity<>(hoaDonService.getAllHoaDonEmployee(pageNumber, pageSize), HttpStatus.OK);
    }


    @GetMapping("hien-thi/ofEmployee")
    public ResponseEntity<List<HoaDonCustomResponse>> getAllHoaDonOfEmployee(
            @RequestParam(name = "trangThaiHD", required = false) Integer trangThaiHD,
            @RequestParam(name = "phuongThucThanhToan", required = false) Integer phuongThucThanhToan,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            Principal principal
    ) {
        String username = principal.getName();
        if (trangThaiHD != null || phuongThucThanhToan != null) {
            return new ResponseEntity<>(hoaDonService.getAllHoaDonOfEmployeeFilter(username, trangThaiHD, phuongThucThanhToan, pageNumber, pageSize), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(hoaDonService.getAllHoaDonOfEmployeeDefault(username, pageNumber, pageSize), HttpStatus.OK);
        }
    }




}
