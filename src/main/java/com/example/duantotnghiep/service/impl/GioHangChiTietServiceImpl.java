package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.repository.GioHangChiTietRepository;
import com.example.duantotnghiep.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    public void themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong) {
        GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
        gioHangChiTiet.setId(UUID.randomUUID());

        // Tạo một đối tượng GioHang để thiết lập mối quan hệ với GioHangChiTiet
        GioHang gioHang = new GioHang();
        gioHang.setId(idGioHang);

        gioHangChiTiet.setGioHang(gioHang);
        // Tạo một đối tượng SanPhamChiTiet để thiết lập mối quan hệ với GioHangChiTiet
        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
        sanPhamChiTiet.setId(idSanPhamChiTiet);

        gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);
        //set so luong
        gioHangChiTiet.setSoLuong(soLuong);

        gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public List<GioHangCustom> loadGH() {
        return gioHangChiTietRepository.loadOnGioHang();
    }
}
