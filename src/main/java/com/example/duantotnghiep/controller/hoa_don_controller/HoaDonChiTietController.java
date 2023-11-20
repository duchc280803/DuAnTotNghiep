package com.example.duantotnghiep.controller.hoa_don_controller;

import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.HoaDonChiTiet;
import com.example.duantotnghiep.request.TraHangRequest;
import com.example.duantotnghiep.repository.HoaDonChiTietRepository;
import com.example.duantotnghiep.request.TrangThaiHoaDonRequest;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.XacNhanThanhToanRequest;
import com.example.duantotnghiep.response.*;
import com.example.duantotnghiep.service.hoa_don_service.impl.HoaDonChiTietServiceImpl;
import com.example.duantotnghiep.service.hoa_don_service.impl.TrangThaiHoaDonServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/hoa-don-chi-tiet/")
public class HoaDonChiTietController {

    @Autowired
    private HoaDonChiTietServiceImpl hoaDonChiTietService;

    @Autowired
    private TrangThaiHoaDonServiceImpl trangThaiHoaDonService;

    @GetMapping("hien-thi-don/{idHoaDon}")
    public ResponseEntity<ThongTinDonHang> viewThongTinDonHang(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getThongTinDonHang(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-san-pham/{idHoaDon}")
    public ResponseEntity<List<SanPhamHoaDonChiTietResponse>> getSanPhamHDCT(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getSanPhamHDCT(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-lich-su/{idHoaDon}")
    public ResponseEntity<List<HinhThucThanhToanResponse>> getLichSuThanhToan(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getLichSuThanhToan(idHoaDon), HttpStatus.OK);
    }

    @GetMapping("hien-thi-trang-thai/{idHoaDon}")
    public ResponseEntity<List<TrangThaiHoaDonResponse>> getAllTrangThaiHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getAllTrangThaiHoaDon(idHoaDon), HttpStatus.OK);
    }

    @PostMapping("confirm-order/{hoadonId}")
    public ResponseEntity<MessageResponse> confirmOrder(
            @PathVariable UUID hoadonId,
            @RequestBody TrangThaiHoaDonRequest request, Principal principal) {
        return new ResponseEntity<>(trangThaiHoaDonService.confirmOrder(hoadonId, request, principal.getName()), HttpStatus.CREATED);
    }

    @PostMapping("confirm-thanh-toan/{hoadonId}")
    public ResponseEntity<String> confirmThanhToan(@PathVariable UUID hoadonId, @RequestBody XacNhanThanhToanRequest request) {
        hoaDonChiTietService.confirmThanhToan(hoadonId, request);
        return ResponseEntity.ok("Hóa đơn đã được thanh toán.");
    }

    @GetMapping("thanh-tien/{idHoaDon}")
    public ResponseEntity<MoneyResponse> getAllMoneyByHoaDon(@PathVariable(name = "idHoaDon") UUID idHoaDon) {
        return new ResponseEntity<>(hoaDonChiTietService.getMoneyByHoaDon(idHoaDon), HttpStatus.OK);
    }

    @PostMapping("them-san-pham")
    public ResponseEntity<MessageResponse> themSanPhamVaoGioHangChiTiet(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "idSanPhamChiTiet") UUID idSanPhamChiTiet,
            @RequestParam(name = "soLuong") int soLuong) {
        return new ResponseEntity<>(
                hoaDonChiTietService.themSanPhamVaoHoaDonChiTiet(idHoaDon, idSanPhamChiTiet, soLuong),
                HttpStatus.CREATED);
    }

    @PutMapping("update-quantity")
    public ResponseEntity<String> capNhatSL(
            @RequestParam(name = "idHoaDonChiTiet") UUID idHoaDonChiTiet,
            @RequestParam(name = "quantity") Integer quantity) {
        try {
            hoaDonChiTietService.capNhatSoLuong(idHoaDonChiTiet, quantity);
            return ResponseEntity.ok("Số lượng đã được cập nhật.(-> nên xem lại Console log)");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm trong giỏ hàng.");
        }
    }

    @PostMapping("thanh-toan-hoa-don-online")
    public ResponseEntity<MessageResponse> createTransaction(
            @RequestParam(name = "idHoaDon") UUID idHoaDon,
            @RequestParam(name = "id") UUID id,
            @RequestBody TransactionRequest transactionRequest
    ) {
        return new ResponseEntity<>(hoaDonChiTietService.createTransaction(idHoaDon, id, transactionRequest), HttpStatus.CREATED);
    }

    @GetMapping("status-order/{id}")
    public ResponseEntity<HoaDon> getStatusOrder(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(hoaDonChiTietService.findByHoaDon(id), HttpStatus.OK);
    }

    @GetMapping("detail-product")
    public ResponseEntity<ProductDetailDTOResponse> getDetailSanPham(@RequestParam UUID idhdct) {
        return new ResponseEntity<>(hoaDonChiTietService.getDetailSanPham(idhdct), HttpStatus.OK);
    }

    @PostMapping("tra-hang")
    public ResponseEntity<MessageResponse> createOrUpdate(@RequestParam("idhdct") UUID idhdct,
                                                          @RequestBody TraHangRequest traHangRequest) {
        MessageResponse response = hoaDonChiTietService.createOrUpdate(idhdct, traHangRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete")
    public ResponseEntity<MessageResponse> deleteOrderDetail(@RequestParam(name = "id") UUID id) {
        hoaDonChiTietService.deleteOrderDetail(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("tra-hang/{id}")
    public ResponseEntity<?> traHang(@PathVariable(name = "id") UUID id) {
        return new ResponseEntity<>(hoaDonChiTietService.traHang(id), HttpStatus.OK);
    }

}
