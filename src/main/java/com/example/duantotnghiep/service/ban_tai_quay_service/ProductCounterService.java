package com.example.duantotnghiep.service.ban_tai_quay_service;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;

import java.util.List;
import java.util.UUID;

public interface ProductCounterService {

    List<ChiTietSanPhamCustom> getAll(Integer pageNumber, Integer pageSize);

    List<ChiTietSanPhamCustom> getSanPhamLienQuan(UUID idthuonghieu, Integer pageNumber, Integer pageSize);

    ChiTietSanPhamCustom getOne(String name);

    List<ChiTietSanPhamCustom> searchByName(String name);

    List<ChiTietSanPhamCustom> filterBrand(String name);

    List<ChiTietSanPhamCustom> filterCategory(String name);

    List<ChiTietSanPhamCustom> filterSole(String name);

    List<ChiTietSanPhamCustom> filterOrigin(String name);

    List<ChiTietSanPhamCustom> filterSize(Integer size);

    List<ChiTietSanPhamCustom> filterMaterial(String name);

    List<ChiTietSanPhamCustom> filterColor(String name);

    Integer soLuong(UUID id);

}
