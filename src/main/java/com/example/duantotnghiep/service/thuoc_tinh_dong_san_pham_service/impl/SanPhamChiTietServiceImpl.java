package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.repository.ChiTietSanPhamRepository;
import com.example.duantotnghiep.response.SanPhamChiTietResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SanPhamChiTietServiceImpl implements SanPhamChiTietService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<SanPhamChiTietResponse> getAll(UUID id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.getAllSanPhamChiTiet(id, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finByTrangThai(UUID id, Integer trangThai, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finByTrangThai(id, trangThai, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finByKey(UUID id, String key, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finByKey(id, key, pageable);
        return sanPhamChiTiet.getContent();
    }

    @Override
    public List<SanPhamChiTietResponse> finBySize(UUID id, Integer size, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamChiTietResponse> sanPhamChiTiet = chiTietSanPhamRepository.finBySize(id, size, pageable);
        return sanPhamChiTiet.getContent();
    }
}
