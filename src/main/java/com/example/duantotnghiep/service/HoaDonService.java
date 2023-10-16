package com.example.duantotnghiep.service;

import com.example.duantotnghiep.request.HoaDonThanhToanRequest;
import com.example.duantotnghiep.response.HoaDonCustomResponse;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;

import java.util.List;

public interface HoaDonService {

    MessageResponse taoHoaDon(String name);

    List<HoaDonResponse> viewHoaDonTaiQuay();

    MessageResponse updateHoaDon(HoaDonThanhToanRequest hoaDonThanhToanRequest);

    List<HoaDonCustomResponse> getAllHoaDonAdmin(Integer pageNumber, Integer pageSize);

    List<HoaDonCustomResponse> getAllHoaDonAdminFilter(Integer trangThaiHD, Integer phuongThucThanhToan, Integer loaiDon, Integer pageNumber, Integer pageSize);

    List<HoaDonCustomResponse> getAllHoaDonEmployee(Integer pageNumber, Integer pageSize);

    List<HoaDonCustomResponse> getAllHoaDonOfEmployeeFilter(String username, Integer trangThaiHD, Integer phuongThucThanhToan,Integer loaiDon, Integer pageNumber, Integer pageSize);

    List<HoaDonCustomResponse> getAllHoaDonOfEmployeeDefault(String username, Integer pageNumber, Integer pageSize);


}
