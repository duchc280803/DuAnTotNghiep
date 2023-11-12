package com.example.duantotnghiep.service.giam_gia_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.UpdateGiamGiaResquest;
import com.example.duantotnghiep.response.GiamGiaDetailResponse;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.giam_gia_service.GiamGiaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GiamGiaServiceimpl implements GiamGiaService {

    @Autowired
    private GiamGiaRepository Repository;

    @Autowired
    private SpGiamGiaRepository spggRepository;

    @Autowired
    private SanPhamRepository spRepository;

    @Override
    public List<GiamGiaResponse> getAll() {
        return Repository.listGiamGia();
    }

    @Override
    public Page<GiamGiaResponse> getAll(Pageable pageable) {
        return Repository.listGiamGias(pageable);
    }

    @Override
    public List<ProductDetailResponse> getAllProduct() {
        return Repository.listProductResponse();
    }
    // @Override
    //// @Transactional
    // public MessageResponse updateGiamGia(UUID id, UpdateGiamGiaResquest
    // updateGiamGiaRequest) {
    // // Kiểm tra xem đối tượng GiamGia có tồn tại không
    // GiamGia existingGiamGia = Repository.findById(id).orElse(null);
    //
    // if (existingGiamGia != null) {
    // // Cập nhật thông tin của đối tượng GiamGia
    // existingGiamGia.setMaGiamGia(updateGiamGiaRequest.getMaGiamGia());
    // existingGiamGia.setTenGiamGia(updateGiamGiaRequest.getTenGiamGia());
    // existingGiamGia.setNgayBatDau(updateGiamGiaRequest.getNgayBatDau());
    // existingGiamGia.setNgayKetThuc(updateGiamGiaRequest.getNgayKetThuc());
    // existingGiamGia.setHinhThucGiam(updateGiamGiaRequest.getHinhThucGiam());
    // existingGiamGia.setTrangThai(updateGiamGiaRequest.getTrangThai());
    //
    // // Lưu cập nhật vào Repository
    // Repository.save(existingGiamGia);
    // // Xóa tất cả các liên kết giảm giá của sản phẩm với đối tượng giảm giá cũ
    //// spggRepository.deleteByGiamGia(existingGiamGia);
    // // Lặp qua danh sách sản phẩm mới và tạo các liên kết giảm giá mới
    //
    //
    // // Trả về thông báo thành công
    // return MessageResponse.builder().message("Cập nhật Thành Công").build();
    // } else {
    // // Handle the case where the discount is not found
    // return MessageResponse.builder().message("Không tìm thấy giảm giá").build();
    // }
    // }

    // @Override
    // public void updateGiamGia(UUID giamGiaId, GiamGiaRequest
    // updateGiamGiaRequest) {
    // Repository.updateGiamGia(
    // giamGiaId,
    // updateGiamGiaRequest.getTenGiamGia(),
    // updateGiamGiaRequest.getNgayBatDau(),
    // updateGiamGiaRequest.getNgayKetThuc(),
    // updateGiamGiaRequest.getHinhThucGiam(),
    // updateGiamGiaRequest.getTrangThai()
    // );
    // }

    @Override
    @Transactional
    public MessageResponse updateGiamGia(UUID id, UpdateGiamGiaResquest updateGiamGiaRequest) {
        // Kiểm tra xem đối tượng GiamGia có tồn tại không
        GiamGia existingGiamGia = Repository.findById(id).orElse(null);

        if (existingGiamGia != null) {
            // Cập nhật thông tin của đối tượng GiamGia
            existingGiamGia.setMaGiamGia(updateGiamGiaRequest.getMaGiamGia());
            existingGiamGia.setTenGiamGia(updateGiamGiaRequest.getTenGiamGia());
            existingGiamGia.setNgayBatDau(updateGiamGiaRequest.getNgayBatDau());
            existingGiamGia.setNgayKetThuc(updateGiamGiaRequest.getNgayKetThuc());
            existingGiamGia.setHinhThucGiam(updateGiamGiaRequest.getHinhThucGiam());
            existingGiamGia.setTrangThai(updateGiamGiaRequest.getTrangThai());

            // Lưu cập nhật vào Repository
            Repository.save(existingGiamGia);
            // Xóa tất cả các liên kết giảm giá của sản phẩm với đối tượng giảm giá cũ
            spggRepository.deleteByGiamGia(existingGiamGia);
            // Lặp qua danh sách sản phẩm mới và tạo các liên kết giảm giá mới
            for (UUID sanPhamId : updateGiamGiaRequest.getIdsanpham()) {
                SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
                if (sanPham != null) {
                    SpGiamGia spGiamGia = new SpGiamGia();
                    spGiamGia.setId(UUID.randomUUID());
                    spGiamGia.setMucGiam(updateGiamGiaRequest.getMucGiam());
                    spGiamGia.setGiamGia(existingGiamGia);
                    spGiamGia.setSanPham(sanPham);
                    if (updateGiamGiaRequest.getHinhThucGiam() == 1) {
                        // HinhThucGiam = 1
                        // dongia = muc giam
                        BigDecimal dongia = BigDecimal.valueOf(updateGiamGiaRequest.getMucGiam());
                        spGiamGia.setDonGia(dongia);
                        // donGiaKhiGiam = gia ban - dongia
                        BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    } else if (updateGiamGiaRequest.getHinhThucGiam() == 2) {
                        // HinhThucGiam = 2
                        // dongia = gia ban x (muc giam / 100)
                        BigDecimal dongia = sanPham.getGiaBan().multiply(
                                BigDecimal.valueOf(updateGiamGiaRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                        // donGiaKhiGiam = gia ban -
                        spGiamGia.setDonGia(dongia);
                        BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                        // sanpham.giaban =
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    }

                    spggRepository.save(spGiamGia);
                    spRepository.save(sanPham);
                } else {
                    // Handle the case where the product is not found
                }
            }

            // Trả về thông báo thành công
            return MessageResponse.builder().message("Cập nhật Thành Công").build();
        } else {
            // Handle the case where the discount is not found
            return MessageResponse.builder().message("Không tìm thấy giảm giá").build();
        }
    }

    @Override
    @Transactional
    public MessageResponse updateGiamGiaStaus(UUID id) {
        // Kiểm tra xem đối tượng GiamGia có tồn tại không
        GiamGia existingGiamGia = Repository.findById(id).orElse(null);

        if (existingGiamGia != null) {

            spggRepository.deleteByGiamGia(existingGiamGia);
            return MessageResponse.builder().message("Cập nhật Thành Công").build();
        } else {
            // Handle the case where the discount is not found
            return MessageResponse.builder().message("Không tìm thấy giảm giá").build();
        }
    }

    @Override
    public List<GiamGiaResponse> findbyValueString(String key) {
        return Repository.findbyValueString(key);
    }

    @Override
    public List<ProductDetailResponse> findbyProduct(String key) {
        return Repository.ProductDetailResponse(key);
    }

    @Override
    public List<GiamGiaResponse> findbyValueDate(Date key1, Date key2) {
        return Repository.findbyValueDate(key1, key2);
    }

    @Override
    public List<GiamGiaResponse> findbyValueStatus(Integer key) {
        return Repository.findbyValueStatus(key);
    }

    @Override
    public List<GiamGiaResponse> checkAndSetStatus() {
        List<GiamGia> giamGiaList = Repository.findAll();
        Date currentDate = new Date(); // Ngày hiện tại

        for (GiamGia giamGia : giamGiaList) {
            if (giamGia.getNgayKetThuc().before(currentDate)) {
                // Nếu ngày kết thúc đã qua so với ngày hiện tại
                if (giamGia.getTrangThai() == 1) {
                    // Nếu trạng thái là 1 (đang hoạt động), thì cập nhật trạng thái thành 2 (đã kết
                    // thúc)
                    giamGia.setTrangThai(2);
                    Repository.save(giamGia);
                }
            }
        }

        return Repository.listGiamGia();
    }

    @Override
    public List<ProductDetailResponse> ListSearch(UUID id) {
        return Repository.ListSearchDanhMuc(id);
    }

    @Override
    public List<GiamGiaResponse> ListGiamGiaDeatil(UUID id) {
        return Repository.listGiamGiaDetail(id);
    }

    @Override
    public MessageResponse createGiamGia(GiamGiaRequest createKhachRequest) {
        // Tạo đối tượng GiamGia
        GiamGia giamGia = new GiamGia();
        giamGia.setId(UUID.randomUUID());
        giamGia.setMaGiamGia(createKhachRequest.getMaGiamGia());
        giamGia.setTenGiamGia(createKhachRequest.getTenGiamGia());
        giamGia.setNgayBatDau(createKhachRequest.getNgayBatDau());
        giamGia.setNgayKetThuc(createKhachRequest.getNgayKetThuc());
        giamGia.setHinhThucGiam(createKhachRequest.getHinhThucGiam());
        giamGia.setTrangThai(createKhachRequest.getTrangThai());
        Repository.save(giamGia);

        for (UUID sanPhamId : createKhachRequest.getIdsanpham()) {
            SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
            if (sanPham != null) {
                SpGiamGia spGiamGia = new SpGiamGia();
                spGiamGia.setId(UUID.randomUUID());
                spGiamGia.setMucGiam(createKhachRequest.getMucGiam());
                spGiamGia.setGiamGia(giamGia);
                spGiamGia.setSanPham(sanPham);

                if (createKhachRequest.getHinhThucGiam() == 1) {
                    // HinhThucGiam = 1
                    // dongia = muc giam
                    BigDecimal dongia = BigDecimal.valueOf(createKhachRequest.getMucGiam());
                    spGiamGia.setDonGia(dongia);
                    // donGiaKhiGiam = gia ban - dongia
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                } else if (createKhachRequest.getHinhThucGiam() == 2) {
                    // HinhThucGiam = 2
                    // dongia = gia ban x (muc giam / 100)
                    BigDecimal dongia = sanPham.getGiaBan().multiply(
                            BigDecimal.valueOf(createKhachRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                    // donGiaKhiGiam = gia ban -
                    spGiamGia.setDonGia(dongia);
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    // sanpham.giaban =
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                }

                spggRepository.save(spGiamGia);
                spRepository.save(sanPham);
            } else {
                // Handle the case where the product is not found
            }
        }

        // danh muc
        List<UUID> productIds = Repository.findProductIdsByDanhMucId(createKhachRequest.getIdDanhMuc());

        // Associate each product with the discount
        for (UUID sanPhamId : productIds) {
            SanPham sanPham = spRepository.findById(sanPhamId).orElse(null);
            if (sanPham != null) {
                SpGiamGia spGiamGia = new SpGiamGia();
                spGiamGia.setId(UUID.randomUUID());
                spGiamGia.setMucGiam(createKhachRequest.getMucGiam());
                spGiamGia.setGiamGia(giamGia);
                spGiamGia.setSanPham(sanPham);

                if (createKhachRequest.getHinhThucGiam() == 1) {
                    // HinhThucGiam = 1
                    // dongia = muc giam
                    BigDecimal dongia = BigDecimal.valueOf(createKhachRequest.getMucGiam());
                    spGiamGia.setDonGia(dongia);
                    // donGiaKhiGiam = gia ban - dongia
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    sanPham.setGiaBan(donGiaKhiGiam);
                } else if (createKhachRequest.getHinhThucGiam() == 2) {
                    // HinhThucGiam = 2
                    // dongia = gia ban x (muc giam / 100)
                    BigDecimal dongia = sanPham.getGiaBan().multiply(
                            BigDecimal.valueOf(createKhachRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                    // donGiaKhiGiam = gia ban -
                    spGiamGia.setDonGia(dongia);
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    // sanpham.giaban =
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    sanPham.setGiaBan(donGiaKhiGiam);
                }

                spggRepository.save(spGiamGia);
                spRepository.save(sanPham);
            } else {
                // Handle the case where the product is not found
            }
        }

        // Trả về thông báo thành công
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }

    @Override
    public boolean checkProductRecordCount(UUID productId) {
        int recordCount = Repository.countByProductId(productId);
        return recordCount < 9;
    }

    @Override
    public boolean isTenGiamGiaExists(String tenGiamGia) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isTenGiamGiaExists'");
    }

}
