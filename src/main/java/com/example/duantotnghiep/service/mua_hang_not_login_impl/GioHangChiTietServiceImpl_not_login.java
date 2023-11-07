package com.example.duantotnghiep.service.mua_hang_not_login_impl;

import com.example.duantotnghiep.entity.GioHang;
import com.example.duantotnghiep.entity.GioHangChiTiet;
import com.example.duantotnghiep.entity.SanPhamChiTiet;
import com.example.duantotnghiep.mapper.GioHangCustom_not_login;
import com.example.duantotnghiep.mapper.NameAndQuantityCart_Online;
import com.example.duantotnghiep.mapper.TongTienCustom_Online;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.GioHangChiTietRepository_not_login;
import com.example.duantotnghiep.repository.mua_hang_not_login_repo.GioHangRepository_not_login;
import com.example.duantotnghiep.service.mua_hang_not_login_service.GioHangChiTietService_not_login;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GioHangChiTietServiceImpl_not_login implements GioHangChiTietService_not_login {
    @Autowired
    private GioHangChiTietRepository_not_login gioHangChiTietRepository;

    @Autowired
    GioHangRepository_not_login gioHangRepository_not_login;

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
            // Set số lượng
            gioHangChiTiet.setSoLuong(soLuong);

            // Tạo một đối tượng SanPhamChiTiet để set mối quan hệ với GioHangChiTiet
            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(idSanPhamChiTiet);

            //set quantity product in stock when adding to cart
//            sanPhamChiTiet.setSoLuong(sanPhamChiTiet.getSoLuong()-soLuong);

            gioHangChiTiet.setSanPhamChiTiet(sanPhamChiTiet);

        }

        // Lưu hoặc cập nhật GioHangChiTiet
        gioHangChiTietRepository.save(gioHangChiTiet);
    }

    @Override
    public List<GioHangCustom_not_login> loadGH(UUID idgh) {
        return gioHangChiTietRepository.loadOnGioHang(idgh);
    }

    @Override
    public List<TongTienCustom_Online> getTongTien(UUID idgh) {
        return gioHangChiTietRepository.getTongTien(idgh);
    }

    @Override
    public List<NameAndQuantityCart_Online> getNameAndQuantity(UUID idgh) {
        return gioHangChiTietRepository.getNameAndQuantity(idgh);
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
    //xoa san pham khoi gio hang
    public void xoaSanPhamKhoiGioHang(UUID idgiohangchitiet) {
        System.out.println(idgiohangchitiet);
        Optional<GioHangChiTiet> optionalGioHangChiTiet = gioHangChiTietRepository.findById(idgiohangchitiet);

        if (optionalGioHangChiTiet.isPresent()) {
            GioHangChiTiet gioHangChiTiet = optionalGioHangChiTiet.get();
            gioHangChiTietRepository.delete(gioHangChiTiet);
        } else {
            // Xử lý tình huống khi không tìm thấy sản phẩm trong giỏ hàng
            System.out.println("Không tìm thấy sản phẩm trong giỏ hàng");
        }
    }



    //xóa tất cả sản phẩm trong giỏ hàng
    public void xoaTatCaSanPhamKhoiGioHang(UUID idGioHang) {
        // Tìm giỏ hàng dựa trên idgiohang
        Optional<GioHang> gioHangOptional = gioHangRepository_not_login.findById(idGioHang);

        if (gioHangOptional.isPresent()) {
            GioHang gioHang = gioHangOptional.get();

            // Lấy danh sách chi tiết giỏ hàng
            List<GioHangChiTiet> chiTietList = gioHang.getGioHangChiTietList();

            // Xóa tất cả chi tiết giỏ hàng
            for (GioHangChiTiet chiTiet : chiTietList) {
                gioHangChiTietRepository.delete(chiTiet);
            }

            // Cập nhật lại giỏ hàng sau khi xóa
            gioHang.setGioHangChiTietList(new ArrayList<>()); // Đặt lại danh sách chi tiết giỏ hàng thành rỗng
            gioHangRepository_not_login.save(gioHang);

        }else {
            throw new EntityNotFoundException("K tìm thấy id trong giỏ hàng");
        }
    }


}
