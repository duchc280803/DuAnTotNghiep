package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.SanPhamDTOResponse;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SanPhamServiceImpl implements SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    public List<SanPhamResponse> getNewProduct() {
        return sanPhamRepository.getNewProduct();
    }

    @Override
    public List<SanPhamDTOResponse> getHoaDonByFilter(Integer trangThai, UUID idDanhMuc, UUID idThuongHieu, UUID idKieuDe, UUID idXuatXu, String maSanPham, String tenSanPham, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamDTOResponse> pageList = sanPhamRepository.getHoaDonByFilter(trangThai, idDanhMuc, idThuongHieu, idKieuDe, idXuatXu, maSanPham, tenSanPham, pageable);
        return pageList.getContent();
    }

}
