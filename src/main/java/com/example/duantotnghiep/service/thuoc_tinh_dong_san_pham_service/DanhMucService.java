package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.request.KieuDeRequest;
import com.example.duantotnghiep.response.MessageResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Service
public interface DanhMucService {

    List<DanhMuc> getAll();

    List<DanhMuc> getAllDanhMuc(Integer trangThai, String tenDanhMuc, Integer pageNumber, Integer pageSize);

    DanhMuc getById(UUID id);

    MessageResponse create(DanhMucRequest danhMucRequest);

    MessageResponse update(UUID id, DanhMucRequest danhMucRequest);

    MessageResponse delete(UUID id);

}
