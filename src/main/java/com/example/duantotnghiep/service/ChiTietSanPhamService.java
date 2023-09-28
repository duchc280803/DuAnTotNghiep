package com.example.duantotnghiep.service;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.mapper.SanPhamGetAllSizeCustom;

import java.util.List;
import java.util.UUID;

public interface ChiTietSanPhamService {
    List<ChiTietSanPhamCustom> getAll();

    List<ChiTietSanPhamCustom> searchByName(String name);

    List<SanPhamGetAllSizeCustom> getByIdSp(UUID id);

    List<ChiTietSanPhamCustom> findByDanhMuc(UUID idDanhMuc);

    List<ChiTietSanPhamCustom> findByXuatSu(UUID idXuatSu);

    List<ChiTietSanPhamCustom> findByThuongHieu(UUID idThuongHieu);

    List<ChiTietSanPhamCustom> findByKieuDe(UUID idKieuDe);

    List<ChiTietSanPhamCustom> findByChatLieu(UUID idChatLieu);

    List<ChiTietSanPhamCustom> findByMauSac(UUID idMauSac);
}
