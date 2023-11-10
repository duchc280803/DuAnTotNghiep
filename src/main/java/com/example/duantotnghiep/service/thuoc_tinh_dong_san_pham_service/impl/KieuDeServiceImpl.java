package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.repository.KieuDeRepository;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.KieuDeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KieuDeServiceImpl implements KieuDeService {

    @Autowired
    private KieuDeRepository kieuDeRepository;

    @Override
    public List<KieuDe> getAll() {
        return kieuDeRepository.findByTrangThai(1);
    }

    @Override
    public KieuDe getById(UUID id) {
        return kieuDeRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(KieuDeRequest request) {
        KieuDe kieuDe = new KieuDe();
        kieuDe.setId(UUID.randomUUID());
        kieuDe.setTenDe(request.getTenKieuDe());
        kieuDe.setTrangThai(request.getTrangThai());
        kieuDeRepository.save(kieuDe);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, KieuDeRequest request) {
        Optional<KieuDe> kieuDeOptional= kieuDeRepository.findById(id);
        if (kieuDeOptional.isPresent()) {
            KieuDe kieuDe = kieuDeOptional.get();
            kieuDe.setTenDe(request.getTenKieuDe());
            kieuDe.setTrangThai(request.getTrangThai());
            kieuDeRepository.save(kieuDe);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<KieuDe> kieuDeOptional= kieuDeRepository.findById(id);
        if (kieuDeOptional.isPresent()) {
            KieuDe kieuDe = kieuDeOptional.get();
            kieuDe.setTrangThai(2);
            kieuDeRepository.save(kieuDe);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }


}
