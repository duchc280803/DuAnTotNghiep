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
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangChiTietServiceImpl implements GioHangChiTietService {
    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Override
    public void themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong) {
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chi tiết chưa
        GioHangChiTiet gioHangChiTiet = gioHangChiTietRepository.findByGioHang_IdAndSanPhamChiTiet_Id(idGioHang, idSanPhamChiTiet);

        if (gioHangChiTiet != null) {
            // Sản phẩm đã tồn tại trong giỏ hàng chi tiết, cập nhật số lượng
            gioHangChiTiet.setSoLuong(gioHangChiTiet.getSoLuong() + soLuong);
        } else {
            // Sản phẩm chưa tồn tại trong giỏ hàng chi tiết, tạo mới
            gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setId(UUID.randomUUID());

            // Tạo một đối tượng GioHang để set mối quan hệ với GioHangChiTiet
            GioHang gioHang = new GioHang();
            gioHang.setId(idGioHang);

            gioHangChiTiet.setGioHang(gioHang);

            // Tạo một đối tượng SanPhamChiTiet để set mối quan hệ với GioHangChiTiet
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(idSanPhamChiTiet);

            gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);

            // Set số lượng
            gioHangChiTiet.setSoLuong(soLuong);
        }

        // Lưu hoặc cập nhật GioHangChiTiet
        gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public List<GioHangCustom> loadGH(String name) {
        return gioHangChiTietRepository.loadOnGioHang(name);
    }

    // Cập nhật số lượng trong GioHangChiTiet
    public void capNhatSoLuong(UUID idgiohangchitiet, int soLuongMoi) {
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idgiohangchitiet);
        System.out.println(idgiohangchitiet);
//        System.out.println(optionalGioHangChiTiet.get());

        // Xử lý tình huống khi tìm thấy sản phẩm trong giỏ hàng
        if (optionalGioHangChiTiet.isPresent()) {

            GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();

            gioHangChiTiet.setSoLuong(soLuongMoi);

            gioHangChiTietRepository.save(gioHangChiTiet);
        } else {
            // Xử lý tình huống khi không tìm thấy sản phẩm trong giỏ hàng
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }

    }
}
