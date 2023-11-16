package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ProductRequest;
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.SanPhamDTOResponse;
import com.example.duantotnghiep.response.SanPhamResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SanPhamServiceImpl implements SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private ThuongHieuRepository thuongHieuRepository;

    @Autowired
    private DanhMucRepository danhMucRepository;

    @Autowired
    private XuatSuRepository xuatSuRepository;

    @Autowired
    private KieuDeRepository kieuDeRepository;

    @Override
    public List<SanPhamResponse> getNewProduct() {
        return sanPhamRepository.getNewProduct();
    }

    @Override
    public List<SanPhamResponse> getBestSellingProducts() {
        return sanPhamRepository.getBestSellingProducts();
    }

    @Override
    public List<SanPhamDTOResponse> getHoaDonByFilter(Integer trangThai, UUID idDanhMuc, UUID idThuongHieu, UUID idKieuDe, UUID idXuatXu, String maSanPham, String tenSanPham, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<SanPhamDTOResponse> pageList = sanPhamRepository.getHoaDonByFilter(trangThai, idDanhMuc, idThuongHieu, idKieuDe, idXuatXu, maSanPham, tenSanPham, pageable);
        return pageList.getContent();
    }

    @Override
<<<<<<< HEAD
    public MessageResponse createProduct(ProductRequest productRequest) {
        Optional<KieuDe> kieuDe = kieuDeRepository.findById(productRequest.getIdKieuDe());
        Optional<XuatXu> xuatXu = xuatSuRepository.findById(productRequest.getIdXuatXu());
        Optional<DanhMuc> danhMuc = danhMucRepository.findById(productRequest.getIdCategory());
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(productRequest.getIdBrand());
        SanPham sanPham = new SanPham();
        sanPham.setMaSanPham(productRequest.getMaSanPham());
        sanPham.setTenSanPham(productRequest.getProductName());
        sanPham.setMoTa(productRequest.getDescribe());
        sanPham.setGiaBan(productRequest.getPrice());
        sanPham.setBaoHanh(productRequest.getBaoHang());
        sanPham.setDanhMuc(danhMuc.get());
        sanPham.setThuongHieu(thuongHieu.get());
        sanPham.setKieuDe(kieuDe.get());
        sanPham.setXuatXu(xuatXu.get());
        sanPhamRepository.save(sanPham);
        return MessageResponse.builder().message("Thêm thành công").build();
=======
    public List<SanPhamResponse> getNewProductbyId(UUID id) {
       return sanPhamRepository.getNewProductbyId(id);
    }

    @Override
    public List<SanPhamResponse> getBestSellingProductsbyId(UUID id) {
        return sanPhamRepository.getBestSellingProductsbyId(id);
>>>>>>> 116283c2c4aa6309d03797de57417a42fbf2cabb
    }

}
