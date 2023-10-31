package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;

import java.util.List;
import java.util.UUID;

public interface ChiTietSanPhamService {

    List<ChiTietSanPhamCustom> getAll();

    List<ChiTietSanPhamCustom> searchByName(String name);

    SanPhamGetAllResponse getByIdSp(UUID id);

    DetailQuantityToSizeReponse getDetailQuantityToSizeReponse(UUID id, Integer size);

}
