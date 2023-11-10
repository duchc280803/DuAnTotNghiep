package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.repository.XuatSuRepository;
import com.example.duantotnghiep.request.XuatXuRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.XuatXuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class XuatXuServiceImpl implements XuatXuService {

    @Autowired
    private XuatSuRepository xuatSuRepository;


    @Override
    public List<XuatXu> getAll() {
        return xuatSuRepository.findByTrangThai(1);
    }

    @Override
    public XuatXu getById(UUID id) {
        return xuatSuRepository.findById(id).orElse(null);
    }

    @Override
    public MessageResponse create(XuatXuRequest request) {
        XuatXu xuatXu = new XuatXu();
        xuatXu.setTenXuatXu(request.getTenXuatXu());
        xuatXu.setTrangThai(request.getTrangThai());
        xuatSuRepository.save(xuatXu);
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public MessageResponse update(UUID id, XuatXuRequest request) {
        Optional<XuatXu> optionalXuatXu= xuatSuRepository.findById(id);
        if (optionalXuatXu.isPresent()) {
            XuatXu xuatXu = optionalXuatXu.get();
            xuatXu.setTenXuatXu(request.getTenXuatXu());
            xuatXu.setTrangThai(request.getTrangThai());
            xuatSuRepository.save(xuatXu);
            return MessageResponse.builder().message("Cập nhật thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }

    @Override
    public MessageResponse delete(UUID id) {
        Optional<XuatXu> optionalXuatXu= xuatSuRepository.findById(id);
        if (optionalXuatXu.isPresent()) {
            XuatXu xuatXu = optionalXuatXu.get();
            xuatXu.setTrangThai(2);
            xuatSuRepository.save(xuatXu);
            return MessageResponse.builder().message("Delete thành công").build();
        } else {
            return MessageResponse.builder().message("Không tìm thấy thương hiệu với ID: " + id).build();
        }
    }
}
