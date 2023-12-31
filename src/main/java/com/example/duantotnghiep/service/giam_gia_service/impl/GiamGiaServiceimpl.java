package com.example.duantotnghiep.service.giam_gia_service.impl;

import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.mapper.ChiTietSanPhamCustom;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.request.UpdateGiamGiaResquest;
import com.example.duantotnghiep.response.GiamGiaDetailResponse;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.audi_log_service.AuditLogService;
import com.example.duantotnghiep.service.giam_gia_service.GiamGiaService;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class GiamGiaServiceimpl implements GiamGiaService {

    @Autowired
    private GiamGiaRepository Repository;

    @Autowired
    private SpGiamGiaRepository spggRepository;

    @Autowired
    private SanPhamRepository spRepository;

    @Autowired
    private SpGiamGiaRepository spGiamGiaRepository;

    @Override
    public List<GiamGiaResponse> getAll(Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public List<GiamGiaResponse> getAll(Integer trangThai, Integer size, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<GiamGiaResponse> pageList = Repository.listGiamGia(trangThai, size, pageable);
        return pageList.getContent();
    }

    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private TaiKhoanRepository taiKhoanRepository;



    public Long getGiaGiamCuoiCung(UUID id) {
        long tongTienGiam = 0L;
        List<SpGiamGia> spGiamGiaList = spGiamGiaRepository.findBySanPham_Id(id);

        for (SpGiamGia spGiamGia : spGiamGiaList) {
            // Cập nhật tổng tiền giảm đúng cách, không khai báo lại biến tongTienGiam
            if (spGiamGia.getGiaGiam() == null) {
                return tongTienGiam;
            }
            if (spGiamGia.getGiaGiam() != null) {
                tongTienGiam += spGiamGia.getGiaGiam().longValue();
            }

        }

        return tongTienGiam;
    }

    public Long countQuantity(UUID id) {
        long count = spGiamGiaRepository.countSpGiamGia(id);
        return count;
    }

    @Override
    public List<ProductDetailResponse> getAllProduct(Integer pageNumber, Integer pageSize) {
        List<ProductDetailResponse> resultList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Object[]> queryResult = Repository.listProductResponse(pageable);
        for (Object[] result : queryResult.getContent()) {
            UUID id = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            ProductDetailResponse productDetailResponse = new ProductDetailResponse(id, image, tenSanPham, giaBan,
                    countQuantity(id), new BigDecimal(getGiaGiamCuoiCung(id)));
            resultList.add(productDetailResponse);
        }
        return resultList;
    }

    @Override
    @Transactional
    public MessageResponse updateGiamGia(UUID id, UpdateGiamGiaResquest updateGiamGiaRequest, String username)
            throws IOException, CsvValidationException {
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
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
                        spGiamGia.setDonGia(sanPham.getGiaBan());
                        spGiamGia.setGiaGiam(dongia);
                        // donGiaKhiGiam = gia ban - dongia
                        BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                        spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                    } else if (updateGiamGiaRequest.getHinhThucGiam() == 2) {
                        // HinhThucGiam = 2
                        // dongia = gia ban x (muc giam / 100)
                        BigDecimal dongia = sanPham.getGiaBan().multiply(
                                BigDecimal.valueOf(updateGiamGiaRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                        // donGiaKhiGiam = gia ban -
                        spGiamGia.setDonGia(sanPham.getGiaBan());
                        spGiamGia.setGiaGiam(dongia);
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
            auditLogService.writeAuditLogKhuyenmai("Sửa Khuyến Mãi", username, taiKhoanUser.getEmail(), null,
                    "Mã :" + updateGiamGiaRequest.getMaGiamGia() + "," + "Tên:" + updateGiamGiaRequest.getTenGiamGia()
                            + "," + "Mức Giảm : " + updateGiamGiaRequest.getMucGiam() + "," + "Hình Thức Giảm : "
                            + updateGiamGiaRequest.getHinhThucGiam() + "," + "Ngày Bắt Đầu : "
                            + updateGiamGiaRequest.getNgayBatDau() + "," + "Ngày Kết Thúc : "
                            + updateGiamGiaRequest.getNgayKetThuc() + "," + "sản phẩm :"
                            + updateGiamGiaRequest.getIdsanpham(),
                    null, null, null);
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
        List<ProductDetailResponse> resultList = new ArrayList<>();
        List<Object[]> queryResult = Repository.ProductDetailResponse(key);
        for (Object[] result : queryResult) {
            UUID id = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            ProductDetailResponse productDetailResponse = new ProductDetailResponse(id, image, tenSanPham, giaBan,
                    countQuantity(id), new BigDecimal(getGiaGiamCuoiCung(id)));
            resultList.add(productDetailResponse);
        }
        return resultList;
    }

    @Override
    public List<GiamGiaResponse> findbyValueDate(Date key1) {
        return Repository.findbyValueDate(key1);
    }

    @Override
    public List<GiamGiaResponse> findbyValueStatus(Integer key) {
        return Repository.findbyValueStatus(key);
    }

    @Override
    public MessageResponse checkAndSetStatus() {
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
        return MessageResponse.builder().message("Ok").build();
    }

    @Override
    public List<ProductDetailResponse> ListSearch(UUID id) {
        List<ProductDetailResponse> resultList = new ArrayList<>();
        List<Object[]> queryResult = Repository.ListSearchDanhMuc(id);
        for (Object[] result : queryResult) {
            UUID idProduct = (UUID) result[0];
            String image = (String) result[1];
            String tenSanPham = (String) result[2];
            BigDecimal giaBan = (BigDecimal) result[3];
            ProductDetailResponse productDetailResponse = new ProductDetailResponse(idProduct, image, tenSanPham,
                    giaBan, countQuantity(id), new BigDecimal(getGiaGiamCuoiCung(id)));
            resultList.add(productDetailResponse);
        }
        return resultList;
    }

    @Override
    public List<GiamGiaResponse> ListGiamGiaDeatil(UUID id) {
        return Repository.listGiamGiaDetail(id);
    }

    @Override
    public MessageResponse createGiamGia(GiamGiaRequest createKhachRequest, String username)
            throws IOException, CsvValidationException {
        // Tạo đối tượng GiamGia
        TaiKhoan taiKhoanUser = taiKhoanRepository.findByUsername(username).orElse(null);
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
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
                    // donGiaKhiGiam = gia ban - dongia
                    BigDecimal donGiaKhiGiam = sanPham.getGiaBan().subtract(dongia);
                    spGiamGia.setDonGiaKhiGiam(donGiaKhiGiam);
                } else if (createKhachRequest.getHinhThucGiam() == 2) {
                    // HinhThucGiam = 2
                    // dongia = gia ban x (muc giam / 100)
                    BigDecimal dongia = sanPham.getGiaBan().multiply(
                            BigDecimal.valueOf(createKhachRequest.getMucGiam()).divide(BigDecimal.valueOf(100)));
                    // donGiaKhiGiam = gia ban -
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
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
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
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
                    spGiamGia.setDonGia(sanPham.getGiaBan());
                    spGiamGia.setGiaGiam(dongia);
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
        auditLogService.writeAuditLogKhuyenmai("Thêm Khuyến Mãi", username, taiKhoanUser.getEmail(), null,
                "Mã :" + createKhachRequest.getMaGiamGia() + "," + "Tên:" + createKhachRequest.getTenGiamGia() + ","
                        + "Mức Giảm : " + createKhachRequest.getMucGiam() + "," + "Hình Thức Giảm : "
                        + createKhachRequest.getHinhThucGiam() + "," + "Ngày Bắt Đầu : "
                        + createKhachRequest.getNgayBatDau() + "," + "Ngày Kết Thúc : "
                        + createKhachRequest.getNgayKetThuc() + "," + "sản phẩm :" + createKhachRequest.getIdsanpham(),
                null, null, null);
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
