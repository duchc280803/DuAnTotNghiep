package com.example.duantotnghiep.service.account_service.impl;

import com.example.duantotnghiep.entity.LoaiTaiKhoan;
import com.example.duantotnghiep.enums.TypeAccountEnum;
import com.example.duantotnghiep.repository.LoaiTaiKhoanRepository;
import com.example.duantotnghiep.service.account_service.LoaiTaiKhoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoaiTaiKhoanServiceImpl implements LoaiTaiKhoanService {
    @Autowired
    private LoaiTaiKhoanRepository loaiTaiKhoanRepository;

    @Override
    public List<LoaiTaiKhoan> findRoles() {
        TypeAccountEnum manager = TypeAccountEnum.MANAGER;
        TypeAccountEnum staff = TypeAccountEnum.STAFF;
        TypeAccountEnum admin = TypeAccountEnum.ADMIN;
        return loaiTaiKhoanRepository.findByRoles(manager, staff, admin);
    }
}
