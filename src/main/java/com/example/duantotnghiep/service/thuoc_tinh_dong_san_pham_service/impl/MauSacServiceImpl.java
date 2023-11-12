package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.MauSac;
import com.example.duantotnghiep.repository.MauSacRepository;
import com.example.duantotnghiep.request.MauSacRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.MauSacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MauSacServiceImpl implements MauSacService {

    @Autowired
    private MauSacRepository mauSacRepository;

    @Override
    public List<MauSac> getAll() {
        return mauSacRepository.findByTrangThai(1);
    }

    @Override
    public MauSac getById(UUID id) {
        return mauSacRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(MauSacRequest request) {
        MauSac mauSac = new MauSac();
        mauSac.setId(UUID.randomUUID());
        mauSac.setTenMauSac(request.getTenMauSac());
        mauSac.setTrangThai(request.getTrangThai());
        mauSacRepository.save(mauSac);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, MauSacRequest request) {
        Optional<MauSac> optionalMauSac= mauSacRepository.findById(id);
        if (optionalMauSac.isPresent()) {
            MauSac mauSac = optionalMauSac.get();
            mauSac.setTenMauSac(request.getTenMauSac());
            mauSac.setTrangThai(request.getTrangThai());
            mauSacRepository.save(mauSac);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<MauSac> optionalMauSac= mauSacRepository.findById(id);
        if (optionalMauSac.isPresent()) {
            MauSac mauSac = optionalMauSac.get();
            mauSac.setTrangThai(2);
            mauSacRepository.save(mauSac);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }
}
