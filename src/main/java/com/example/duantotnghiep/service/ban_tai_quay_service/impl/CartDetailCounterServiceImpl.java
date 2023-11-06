package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom;
import com.example.duantotnghiep.repository.ChiTietSanPhamRepository;
import com.example.duantotnghiep.repository.GioHangChiTietRepository;
import com.example.duantotnghiep.repository.GioHangRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.CartDetailCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartDetailCounterServiceImpl implements CartDetailCounterService {

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private ChiTietSanPhamRepository chiTietSanPhamRepository;

    @Override
    public MessageResponse themSanPhamVaoGioHangChiTiet(UUID idGioHang, UUID idSanPhamChiTiet, int soLuong) {
        GioHang gioHang = gioHangRepository.findByGioHang(idGioHang);
        if (gioHang == null) {
            return MessageResponse.builder().message("Giỏ Hàng Null").build();
        }

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        GioHangChiTiet ghct = gioHangChiTietRepository.findByGioHangAndSanPhamChiTiet_Id(gioHang, idSanPhamChiTiet);
        SanPhamChiTiet sanPhamChiTiet = chiTietSanPhamRepository.findById(idSanPhamChiTiet).get();
        if (ghct != null) {
            // Nếu sản phẩm đã tồn tại trong giỏ hàng, cập nhật số lượng
            ghct.setSoLuong(ghct.getSoLuong() + soLuong);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, tạo bản ghi mới
            ghct = new GioHangChiTiet();
            ghct.setId(UUID.randomUUID());
            ghct.setGioHang(gioHang);

            sanPhamChiTiet.setId(idSanPhamChiTiet);
            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong() - soLuong);
            ghct.setSanPhamChiTiet(sanPhamChiTiet);

            ghct.setSoLuong(soLuong);
        }

        // Lưu giỏ hàng chi tiết vào cơ sở dữ liệu
        chiTietSanPhamRepository.save(sanPhamChiTiet);
        gioHangChiTietRepository.save(ghct);

        return MessageResponse.builder().message("Thêm thành công").build();
    }

    @Override
    public List<GioHangCustom> loadGH(UUID id, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<GioHangCustom> gioHangCustomPage = gioHangChiTietRepository.loadOnGioHang(id, pageable);
        return gioHangCustomPage.getContent();
    }

    @Override
    public void deleteProductInCart(UUID id) {
        gioHangChiTietRepository.deleteById(id);
    }

    // Cập nhật số lượng trong GioHangChiTiet
    public void capNhatSoLuong(UUID idgiohangchitiet, int soLuongMoi) {
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idgiohangchitiet);
        if (optionalGioHangChiTiet.isPresent()) {
            optionalGioHangChiTiet.get().setSoLuong(soLuongMoi);
            gioHangChiTietRepository.save(optionalGioHangChiTiet.get());
        } else {
            System.out.println("ID sản phẩm chi tiết không tồn tại");
        }
    }
}
