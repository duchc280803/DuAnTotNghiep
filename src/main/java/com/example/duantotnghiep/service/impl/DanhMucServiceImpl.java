package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.repository.DanhMucRepository;
import com.example.duantotnghiep.response.DanhMucResponse;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DanhMucServiceImpl implements DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Override
    public List<DanhMuc> getAll() {
        return danhMucRepository.findAll();
    }

    @Override
    public List<DanhMucResponse> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<DanhMucResponse> pageList = danhMucRepository.getAllDoanhMuc(pageable);
        return pageList.getContent();
    }


}
