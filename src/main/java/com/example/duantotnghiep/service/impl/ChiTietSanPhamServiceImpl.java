package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.ChiTietSanPhamRepository;
import com.example.duantotnghiep.response.DetailQuantityToSizeReponse;
import com.example.duantotnghiep.response.DetailSizeToProductResponse;
import com.example.duantotnghiep.response.SanPhamGetAllResponse;
import com.example.duantotnghiep.service.ChiTietSanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChiTietSanPhamServiceImpl implements ChiTietSanPhamService {
    @Autowired
    ChiTietSanPhamRepository chiTietSanPhamRepository;

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
    public List<DetailSizeToProductResponse> getDetailSizeToSanPham(UUID id) {
        return chiTietSanPhamRepository.getDetailSizeToSanPham(id);
    }

    @Override
    public DetailQuantityToSizeReponse getDetailQuantityToSizeReponse(UUID id, Integer size) {
        return chiTietSanPhamRepository.getDetailQuantityToSizeReponse(id, size);
    }

    //filter
    @Override
    public List<ChiTietSanPhamCustom> findByDanhMuc(UUID idDanhMuc) {
        return chiTietSanPhamRepository.searchByIdDanhMuc(idDanhMuc);
    }

    @Override
    public List<ChiTietSanPhamCustom> findByXuatSu(UUID idXuatSu) {
        return chiTietSanPhamRepository.searchByIdXuatSu(idXuatSu);
    }

    @Override
    public List<ChiTietSanPhamCustom> findByThuongHieu(UUID idThuongHieu) {
        return chiTietSanPhamRepository.searchByIdThuongHieu(idThuongHieu);
    }

    @Override
    public List<ChiTietSanPhamCustom> findByKieuDe(UUID idKieuDe) {
        return chiTietSanPhamRepository.searchByIdKieuDe(idKieuDe);

    }

    @Override
    public List<ChiTietSanPhamCustom> findByChatLieu(UUID idChatLieu) {
        return chiTietSanPhamRepository.searchByIdChatLieu(idChatLieu);

    }

    @Override
    public List<ChiTietSanPhamCustom> findByMauSac(UUID idMauSac) {
        return chiTietSanPhamRepository.searchByIdMauSac(idMauSac);

    }


}
