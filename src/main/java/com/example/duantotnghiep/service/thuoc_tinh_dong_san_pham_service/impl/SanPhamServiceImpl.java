package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.ProductRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
    public List<ProductResponse> findByThuongHieu(Integer pageNumber, Integer pageSize, String value) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByThuongHieu(pageable, value);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> findByKieuDe(Integer pageNumber, Integer pageSize, String value) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByKieuDe(pageable, value);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> findByXuatXu(Integer pageNumber, Integer pageSize, String value) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByXuatXu(pageable, value);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> findByDanhMuc(Integer pageNumber, Integer pageSize, String value) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByDanhMuc(pageable, value);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> findByNameOrCode(Integer pageNumber, Integer pageSize, String value) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByNameOrCode(pageable, value);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> getHoaDonByFilter(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.getAllSanPham(pageable);
        return pageList.getContent();
    }

    @Override
    public List<ProductResponse> findByStatus(Integer pageNumber, Integer pageSize, Integer status) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProductResponse> pageList = sanPhamRepository.findByStatus(pageable, status);
        return pageList.getContent();
    }

    @Override
    public SanPham createProduct(ProductRequest productRequest) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Optional<KieuDe> kieuDe = kieuDeRepository.findById(productRequest.getIdKieuDe());
        Optional<XuatXu> xuatXu = xuatSuRepository.findById(productRequest.getIdXuatXu());
        Optional<DanhMuc> danhMuc = danhMucRepository.findById(productRequest.getIdCategory());
        Optional<ThuongHieu> thuongHieu = thuongHieuRepository.findById(productRequest.getIdBrand());
        SanPham sanPham = new SanPham();
        sanPham.setId(UUID.randomUUID());
        sanPham.setNgayTao(timestamp);
        sanPham.setMaSanPham(productRequest.getMaSanPham());
        sanPham.setTenSanPham(productRequest.getProductName());
        sanPham.setMoTa(productRequest.getDescribe());
        sanPham.setGiaBan(productRequest.getPrice());
        sanPham.setBaoHanh(productRequest.getBaoHang());
        sanPham.setDanhMuc(danhMuc.get());
        sanPham.setThuongHieu(thuongHieu.get());
        sanPham.setKieuDe(kieuDe.get());
        sanPham.setXuatXu(xuatXu.get());
        sanPham.setTrangThai(1);
        sanPhamRepository.save(sanPham);
        return sanPham;
    }

    public List<SanPhamResponse> getNewProductbyId(UUID id) {
       return sanPhamRepository.getNewProductbyId(id);
    }

    @Override
    public List<SanPhamResponse> getBestSellingProductsbyId(UUID id) {
        return sanPhamRepository.getBestSellingProductsbyId(id);
    }

    @Override
    public ProductUpdateResponse findByProduct(UUID id) {
        return sanPhamRepository.findByProduct(id);
    }

    @Override
    public List<ProductDetailUpdateReponse> findByProductDetail(UUID id) {
        return sanPhamRepository.findByProductDetail(id);
    }

}
