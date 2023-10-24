package com.example.duantotnghiep.service.impl;


import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.*;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.ProductDetailResponse;
import com.example.duantotnghiep.service.GiamGiaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GiamGiaServiceimpl implements GiamGiaService {

    @Autowired
    private GiamGiaRepository Repository;
    @Autowired
    private SpGiamGiaRepository spggRepository;
    @Autowired
    private ProductDetailRepository spctRepository;
    @Autowired
    private SanPhamRepository spRepository;
    @Autowired
    private KieuDeRepository kdRepository;
    @Autowired
    private ChatLieuRepository clRepository;
    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private MauSacRepository msRepository;
    @Autowired
    private ImageRepository iRepository;
    @Override
    public List<GiamGiaResponse> getAll() {
//        List<GiamGiaResponse> allGiamGia = Repository.listGiamGia();
//
//        // Lấy ra top 3 bản ghi đầu tiên
//        List<GiamGiaResponse> top3Records = allGiamGia.stream().limit(3).collect(Collectors.toList());
return Repository.listGiamGia();
//        return top3Records;
    }

    @Override
    public List<ProductDetailResponse> getAllProduct() {
        return Repository.ListProductResponse();
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
        return Repository.findbyValueDate(key1,key2);
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
                    // Nếu trạng thái là 1 (đang hoạt động), thì cập nhật trạng thái thành 2 (đã kết thúc)
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
    public MessageResponse createGiamGia(GiamGiaRequest createKhachRequest) {
        // Tạo đối tượng GiamGia
        GiamGia giamGia = new GiamGia();
        giamGia.setId(UUID.randomUUID());
        giamGia.setTenGiamGia(createKhachRequest.getTenGiamGia());
        giamGia.setMaGiamGia(createKhachRequest.getMaGiamGia());
        giamGia.setNgayBatDau(createKhachRequest.getNgayBatDau());
        giamGia.setNgayKetThuc(createKhachRequest.getNgayKetThuc());
        giamGia.setHinhThucGiam(createKhachRequest.getHinhThucGiam());
        giamGia.setTrangThai(createKhachRequest.getTrangThai());

        // Lưu đối tượng GiamGia vào Repository
        Repository.save(giamGia);

//
            SanPham sanPham = spRepository.findById(createKhachRequest.getIdsanpham()).orElse(null);
           ChatLieu cl = clRepository.findById(createKhachRequest.getIdchatLieu()).orElse(null);
        MauSac ms = msRepository.findById(createKhachRequest.getIdmausac()).orElse(null);
        Size s = sizeRepository.findById(createKhachRequest.getIdsize()).orElse(null);
        KieuDe kd = kdRepository.findById(createKhachRequest.getIdkieude()).orElse(null);
            // Tạo đối tượng SanPhamChiTiet

            SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
            sanPhamChiTiet.setId(UUID.randomUUID());
        sanPhamChiTiet.setSanPham(sanPham);
        sanPhamChiTiet.setChatLieu(cl);
        sanPhamChiTiet.setSize(s);
        sanPhamChiTiet.setKieuDe(kd);
        sanPhamChiTiet.setMauSac(ms);
        System.out.println("SanPham ctID: " + sanPhamChiTiet.getId());
        spctRepository.save(sanPhamChiTiet);
// anh
        Image i = new Image();
        i.setId(UUID.randomUUID());
//        i.setTenImage(createKhachRequest.getImage());
        i.setSanPhamChiTiet(sanPhamChiTiet);
i.setIsDefault(true);
i.setTrangThai(1);
iRepository.save(i);
        // Tạo đối tượng SpGiamGia và cài đặt các thuộc tính
        SpGiamGia spGiamGia = new SpGiamGia();
        spGiamGia.setId(UUID.randomUUID());
        spGiamGia.setMucGiam(createKhachRequest.getMucGiam());
        spGiamGia.setGiamGia(giamGia);
        spGiamGia.setSanPhamChiTiet(sanPhamChiTiet);

        // Lưu đối tượng SpGiamGia vào Repository
        spggRepository.save(spGiamGia);

        // Trả về thông báo thành công
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }




}
