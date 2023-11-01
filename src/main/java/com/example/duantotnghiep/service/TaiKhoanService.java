package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.TaiKhoan;

import java.util.Optional;

public interface TaiKhoanService {
    Optional<TaiKhoan> findByUserName(String username);
}