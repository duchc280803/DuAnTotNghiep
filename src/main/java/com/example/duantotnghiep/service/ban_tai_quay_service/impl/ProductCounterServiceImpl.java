package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.ChiTietSanPhamRepository;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.ProductCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductCounterServiceImpl implements ProductCounterService {

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public List<ChiTietSanPhamCustom> getAll() {
        return chiTietSanPhamRepository.getAll();
    }

    @Override
    public List<ChiTietSanPhamCustom> searchByName(String name) {
        return chiTietSanPhamRepository.searchByName(name);
    }

    @Override
    public SanPhamGetAllResponse getByIdSp(UUID id) {
        return chiTietSanPhamRepository.getByIdSp(id);
    }

    @Override
    public DetailQuantityToSizeReponse getDetailQuantityToSizeReponse(UUID id, Integer size) {
        return chiTietSanPhamRepository.getDetailQuantityToSizeReponse(id, size);
    }

}
