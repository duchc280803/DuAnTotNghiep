package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service;

import com.example.duantotnghiep.request.ProductRequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
<<<<<<< HEAD
import com.example.duantotnghiep.response.MessageResponse;
=======
import com.example.duantotnghiep.response.ProductDetailResponse;
>>>>>>> 116283c2c4aa6309d03797de57417a42fbf2cabb
import com.example.duantotnghiep.response.SanPhamDTOResponse;
import com.example.duantotnghiep.response.SanPhamResponse;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface SanPhamService {
    List<SanPhamResponse> getNewProduct();
    List<SanPhamResponse> getBestSellingProducts();
    List<SanPhamDTOResponse> getHoaDonByFilter(Integer trangThai, UUID idDanhMuc, UUID idThuongHieu, UUID idKieuDe, UUID idXuatXu,
           String maSanPham, String tenSanPham, Integer pageNumber, Integer pageSize);
<<<<<<< HEAD

    MessageResponse createProduct(ProductRequest productRequest);
=======
    List<SanPhamResponse> getNewProductbyId( UUID id);
    List<SanPhamResponse> getBestSellingProductsbyId( UUID id);
>>>>>>> 116283c2c4aa6309d03797de57417a42fbf2cabb
}
