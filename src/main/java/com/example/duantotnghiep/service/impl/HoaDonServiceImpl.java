package com.example.duantotnghiep.service.impl;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.AccountRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.response.HoaDonResponse;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class HoaDonServiceImpl implements HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    //TODO Thêm hóa đơn tại quầy
    public MessageResponse taoHoaDon(String name) {

        Optional<TaiKhoan> findByNhanVien = accountRepository.findByUsername(name);

        Random rand = new Random();
        int randomNumber = rand.nextInt(1000); // Số ngẫu nhiên từ 0 đến 999
        String maHd = String.format("HD%03d", randomNumber); // Định dạng thành "HD001", "HD002", ...

        LocalDate localDate = LocalDate.now();
        Date utilDate = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        HoaDon hoaDon = new HoaDon();
        hoaDon.setId(UUID.randomUUID());
        hoaDon.setMa(maHd);
        hoaDon.setNgayTao(utilDate);
        hoaDon.setTrangThai(1);
        hoaDon.setTaiKhoan(findByNhanVien.get());
        hoaDonRepository.save(hoaDon);
        return MessageResponse.builder().message("Tạo hóa đơn thành công").build();
    }

    @Override
    public List<HoaDonResponse> viewHoaDonTaiQuay() {
        return hoaDonRepository.viewHoaDonTaiQuay();
    }
}
