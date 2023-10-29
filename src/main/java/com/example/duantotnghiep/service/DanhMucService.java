package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.response.DanhMucResponse;

import java.util.List;

public interface DanhMucService {

    List<DanhMuc> getAll();

    List<DanhMucResponse> findAll(Integer pageNumber, Integer pageSize);
}
