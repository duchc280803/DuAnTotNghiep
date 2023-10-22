package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.repository.GioHangChiTietRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.GioHangChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Override
    public MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chi tiết chưa
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGioHang(idSanPhamChiTiet);
        GioHang gioHang = gioHangRepository.findByGioHang(idGioHang);
        if (gioHang == null) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }
        if (gioHangChiTiet != null) {
            // Sản phẩm đã tồn tại trong giỏ hàng chi tiết, cập nhật số lượng
            gioHangChiTiet.setSoLuong(gioHangChiTiet.getSoLuong() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet);
        } else {
            // Sản phẩm chưa tồn tại trong giỏ hàng chi tiết, tạo mới
            GioHangChiTiet ghct = new GioHangChiTiet();
            ghct.setId(UUID.randomUUID());

            // Tạo một đối tượng GioHang để set mối quan hệ với GioHangChiTiet

            ghct.setGioHang(gioHang);

            // Tạo một đối tượng SanPhamChiTiet để set mối quan hệ với GioHangChiTiet
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(idSanPhamChiTiet);

            ghct.setSanPhamChiTiet(sanPhamChiTiet);

            // Set số lượng
            ghct.setSoLuong(soLuong);
            gioHangChiTietRepository.save(ghct);
        }
        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public List<GioHangCustom> loadGH(String name) {
        return gioHangChiTietRepository.loadOnGioHang(name);
    }

    // Cập nhật số lượng trong GioHangChiTiet
    public void capNhatSoLuong(UUID idgiohangchitiet, int soLuongMoi) {
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idgiohangchitiet);
        System.out.println(idgiohangchitiet);
        if (optionalGioHangChiTiet.isPresent()) {
            GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();
            gioHangChiTiet.setSoLuong(soLuongMoi);
            gioHangChiTietRepository.save(gioHangChiTiet);
        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }
    }
}
