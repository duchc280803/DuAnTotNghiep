package com.example.duantotnghiep.mapper;

import com.example.duantotnghiep.entity.GiamGia;
import com.example.duantotnghiep.entity.SpGiamGia;
import com.example.duantotnghiep.request.CreateMaGiamGiaCreateRequest;
import com.example.duantotnghiep.request.CreateSpGiamGiaRequest;
import com.example.duantotnghiep.request.GiamGiaRequest;

import java.util.UUID;

public class GiamGiaMapper {

    // TODO Map từ giảm giá qua DTO (CREATE GIẢM GIÁ)
    public static GiamGia giamGiaToDto(CreateMaGiamGiaCreateRequest createMaGiamGiaCreateRequest) {
        GiamGia giamGia = new GiamGia();
        giamGia.setId(UUID.randomUUID());
        giamGia.setMaGiamGia(createMaGiamGiaCreateRequest.getMaGiamGia());
        giamGia.setTenGiamGia(createMaGiamGiaCreateRequest.getTenGiamGia());
        giamGia.setNgayBatDau(createMaGiamGiaCreateRequest.getNgayBatDau());
        giamGia.setNgayKetThuc(createMaGiamGiaCreateRequest.getNgayKetThuc());
        giamGia.setGiaTriGiam(createMaGiamGiaCreateRequest.getGiaTriGiam());
        giamGia.setDieuKienGiamGia(createMaGiamGiaCreateRequest.getDieuKienGiamGia());
        giamGia.setLoaiGiamGia(createMaGiamGiaCreateRequest.getLoaiGiamGia());
        giamGia.setHinhThucGiam(createMaGiamGiaCreateRequest.getHinhThucGiam());
        giamGia.setTrangThai(1);
        giamGia.setSpGiamGiaList(null);
        return giamGia;
    }

    public static GiamGiaRequest mapToGiamGiaRequest(SpGiamGia spGiamGia) {
        return new GiamGiaRequest(
                spGiamGia.getGiamGia().getMaGiamGia(),
                spGiamGia.getGiamGia().getTenGiamGia(),
                spGiamGia.getGiamGia().getLoaiGiamGia(),
                spGiamGia.getGiamGia().getHinhThucGiam(),
                spGiamGia.getSoLuongMa(),
                spGiamGia.getSoLuongDung(),
                spGiamGia.getGiamGia().getNgayBatDau(),
                spGiamGia.getGiamGia().getNgayKetThuc(),
                spGiamGia.getGiamGia().getTrangThai()
        );
    }

}
