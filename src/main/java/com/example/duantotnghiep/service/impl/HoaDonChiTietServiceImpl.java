package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.repository.HoaDonChiTietRepository;
import com.example.duantotnghiep.response.HinhThucThanhToanResponse;
import com.example.duantotnghiep.response.SanPhamHoaDonChiTietResponse;
import com.example.duantotnghiep.response.ThongTinDonHang;
import com.example.duantotnghiep.service.HoaDonChiTietService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HoaDonChiTietServiceImpl implements HoaDonChiTietService {
<<<<<<< HEAD

=======
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
    @Autowired
    HoaDonChiTietRepository hoaDonChiTietRepository;

    @Override
    public ThongTinDonHang getThongTinDonHang(UUID idHoaDon) {
        return hoaDonChiTietRepository.getThongTinDonHang(idHoaDon);
    }

    @Override
    public List<SanPhamHoaDonChiTietResponse> getSanPhamHDCT(UUID idHoaDon) {
        return hoaDonChiTietRepository.getSanPhamHDCT(idHoaDon);
    }

    @Override
    public List<HinhThucThanhToanResponse> getLichSuThanhToan(UUID idHoaDon) {
        return hoaDonChiTietRepository.getLichSuThanhToan(idHoaDon);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
