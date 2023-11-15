package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.entity.KieuDe;
import com.example.duantotnghiep.entity.ThuongHieu;
import com.example.duantotnghiep.entity.XuatXu;
import com.example.duantotnghiep.response.SanPhamDTOResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.*;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/san-pham/")
public class SanPhamController {

    @Autowired
    private DanhMucServiceImpl danhMucService;

    @Autowired
    private XuatXuServiceImpl xuatXuService;

    @Autowired
    private KieuDeServiceImpl kieuDeService;

    @Autowired
    private ThuongHieuServiceImpl thuongHieuService;

    @Autowired
    private SanPhamServiceImpl sanPhamService;

    @GetMapping("/hien-thi")
    public ResponseEntity<List<SanPhamDTOResponse>> filterSanPham(
            @Nullable @RequestParam("trangThai") Integer trangThai,
            @Nullable @RequestParam("idDanhMuc") UUID idDanhMuc,
            @Nullable @RequestParam("idThuongHieu") UUID idThuongHieu,
            @Nullable @RequestParam("idKieuDe") UUID idKieuDe,
            @Nullable @RequestParam("idXuatXu") UUID idXuatXu,
            @Nullable @RequestParam("maSanPham") String maSanPham,
            @Nullable @RequestParam("tenSanPham") String tenSanPham,
            @Nullable @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @Nullable @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize
         ) {
        return new ResponseEntity<>(sanPhamService.getHoaDonByFilter(trangThai, idDanhMuc, idThuongHieu, idKieuDe, idXuatXu, maSanPham, tenSanPham, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("hien-thi-danh-muc")
    public ResponseEntity<List<DanhMuc>> listDanhMuc(){
        return new ResponseEntity<>(danhMucService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-xuat-xu")
    public ResponseEntity<List<XuatXu>> listXuatXu(){
        return new ResponseEntity<>(xuatXuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-thuong-hieu")
    public ResponseEntity<List<ThuongHieu>> listThuongHieu(){
        return new ResponseEntity<>(thuongHieuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi-kieu-de")
    public ResponseEntity<List<KieuDe>> listKieuDe(){
        return new ResponseEntity<>(kieuDeService.getAll(), HttpStatus.OK);
    }

}
