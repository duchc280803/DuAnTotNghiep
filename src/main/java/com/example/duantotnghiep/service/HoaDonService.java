package com.example.duantotnghiep.service;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.request.HoaDonGiaoThanhToanRequest;
import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
<<<<<<< HEAD
import com.example.duantotnghiep.response.*;
=======
import com.example.duantotnghiep.response.HoaDonDTOResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.IdGioHangResponse;
import com.example.duantotnghiep.response.MessageResponse;

import com.example.duantotnghiep.response.ThongTinDonHang;

import com.example.duantotnghiep.response.OrderCounterCartsResponse;
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.UUID;

public interface HoaDonService {

    MessageResponse taoHoaDon(String name);

    List<HoaDonResponse> viewHoaDonTaiQuay(Integer pageNumber, Integer pageSize);


    MessageResponse updateHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest);

    List<HoaDonDTOResponse> getAllHoaDonAdmin(Integer trangThaiHD, Integer loaiDon, String tenNhanVien, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonStaff(Integer trangThaiHD, Integer loaiDon, String ma, String soDienThoai, String username, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonCTTStaff(Integer loaiDon, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);



    List<HoaDonResponse> findByCodeOrder(String ma);

    MessageResponse updateHoaDon(UUID idHoaDon, HoaDonThanhToanRequest hoaDonThanhToanRequest);

    OrderCounterCartsResponse findByHoaDon(UUID id);

    IdGioHangResponse showIdGioHangCt(UUID id);

<<<<<<< HEAD
    public MessageResponse updateHoaDonGiaoTaiQuay(UUID idHoaDon, HoaDonGiaoThanhToanRequest hoaDonGiaoThanhToanRequest);

    List<HoaDonDTOResponse> getAllHoaDonAdmin(Integer trangThaiHD, Integer loaiDon, String tenNhanVien, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonStaff(Integer trangThaiHD, Integer loaiDon, String ma, String soDienThoai, String username, Integer pageNumber, Integer pageSize);

    List<HoaDonDTOResponse> getAllHoaDonCTTStaff(Integer loaiDon, String ma, String soDienThoai, Integer pageNumber, Integer pageSize);

=======
>>>>>>> 2ee2821ddc2018f3497374646b8de782ba7e6791
}
