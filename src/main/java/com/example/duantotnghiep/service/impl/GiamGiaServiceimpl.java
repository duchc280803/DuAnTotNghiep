package com.example.duantotnghiep.service.impl;


import com.example.duantotnghiep.entity.*;
import com.example.duantotnghiep.repository.GiamGiaRepository;
import com.example.duantotnghiep.request.CreateKhachRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;
import com.example.duantotnghiep.response.GiamGiaResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.GiamGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GiamGiaServiceimpl implements GiamGiaService {
    @Autowired
    private GiamGiaRepository Repository;



    @Override
    public List<GiamGiaResponse> getAll() {
        return Repository.listGiamGia();
    }

    @Override
    public List<GiamGiaResponse> findbyValueString(String key) {
        return Repository.findbyValueString(key);
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
//    @Override
//    public MessageResponse createKhachHang(CreateKhachRequest createKhachRequest) {
//        TaiKhoan taiKhoan = new TaiKhoan();
//        taiKhoan.setId(UUID.randomUUID());
//        taiKhoan.setName(createKhachRequest.getHoTen());
//        taiKhoan.setSoDienThoai(createKhachRequest.getSoDienThoai());
//        taiKhoan.setEmail(createKhachRequest.getEmail());
//        taiKhoan.setNgaySinh(createKhachRequest.getNgaySinh());
//        khachHangRepository.save(taiKhoan);
//        DiaChi diaChi = new DiaChi();
//        diaChi.setId(UUID.randomUUID());
//        diaChi.setDiaChi(createKhachRequest.getDiaChi());
//        diaChi.setTaiKhoan(taiKhoan);
//        diaChiRepository.save(diaChi);
//        return MessageResponse.builder().message("Thêm Thành Công").build();
//    }
    @Override
    public MessageResponse createGiamGia(GiamGiaRequest createKhachRequest) {
        GiamGia giamGia = new GiamGia();
        giamGia.setId(UUID.randomUUID());
        giamGia.setTenGiamGia(createKhachRequest.getTenGiamGia());
      Repository.save(giamGia );
//        SanPhamChiTiet sanPhamChiTiet = new SanPhamChiTiet();
//        sanPhamChiTiet.setId(UUID.randomUUID());
//        sanPhamChiTiet.setSanPham( giamGia);
        return MessageResponse.builder().message("Thêm Thành Công").build();
    }



}
