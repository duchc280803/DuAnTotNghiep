package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.repository.ThuongHieuRepository;
import com.example.duantotnghiep.request.ThuongHieuRequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.ThuongHieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ThuongHieuServiceImpl implements ThuongHieuService {

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Override
    public List<ThuongHieu> getAll() {
        return thuongHieuRepository.findByTrangThai(1);
    }


    @Override
    public List<ThuongHieu> getAllThuongHieu(Integer trangThai, String tenThuongHieu, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ThuongHieu> pageList = thuongHieuRepository.getAllThuongHieu(trangThai, tenThuongHieu, pageable);
        return pageList.getContent();
    }

    @Override
    public ThuongHieu getById(UUID id) {
        return thuongHieuRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(ThuongHieuRequest request) {
        ThuongHieu thuongHieu = new ThuongHieu();
        thuongHieu.setId(UUID.randomUUID());
        thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
        thuongHieu.setTrangThai(request.getTrangThai());
        thuongHieuRepository.save(thuongHieu);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, ThuongHieuRequest request) {
        Optional<ThuongHieu> optionalThuongHieu = thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            ThuongHieu thuongHieu = optionalThuongHieu.get();
            thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
            thuongHieu.setTrangThai(request.getTrangThai());
            thuongHieuRepository.save(thuongHieu);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<ThuongHieu> optionalThuongHieu = thuongHieuRepository.findById(id);
        if (optionalThuongHieu.isPresent()) {
            ThuongHieu thuongHieu = optionalThuongHieu.get();
            thuongHieu.setTrangThai(2);
            thuongHieuRepository.save(thuongHieu);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }
}
