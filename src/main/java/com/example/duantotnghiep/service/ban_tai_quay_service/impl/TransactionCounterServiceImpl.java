package com.example.duantotnghiep.service.ban_tai_quay_service.impl;

import com.example.duantotnghiep.config.VnPayConfig;
import com.example.duantotnghiep.entity.HinhThucThanhToan;
import com.example.duantotnghiep.entity.HoaDon;
import com.example.duantotnghiep.entity.TaiKhoan;
import com.example.duantotnghiep.repository.HinhThucThanhToanRepository;
import com.example.duantotnghiep.repository.HoaDonRepository;
import com.example.duantotnghiep.repository.KhachHangRepository;
import com.example.duantotnghiep.request.TransactionRequest;
import com.example.duantotnghiep.request.TransactionVnPayRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.response.TransactionResponse;
import com.example.duantotnghiep.service.ban_tai_quay_service.TransactionCounterService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TransactionCounterServiceImpl implements TransactionCounterService {

    @Autowired
    private HinhThucThanhToanRepository hinhThucThanhToanRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Override
    public MessageResponse createTransaction(UUID idHoaDon, UUID id, TransactionRequest transactionRequest) {
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);

        HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
        hinhThucThanhToan.setId(UUID.randomUUID());
        hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
        hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
        hinhThucThanhToan.setTongSoTien(transactionRequest.getSoTien());
        hinhThucThanhToan.setPhuongThucThanhToan(1);
        hinhThucThanhToan.setCodeTransaction(VnPayConfig.getRandomNumber(8));
        hinhThucThanhToan.setHoaDon(hoaDon.get());
        hinhThucThanhToan.setTrangThai(1);
        hinhThucThanhToanRepository.save(hinhThucThanhToan);
        return MessageResponse.builder().message("Thanh toán thành công").build();
    }

    @Override
    public List<TransactionResponse> findAllTran(UUID id) {
        return hinhThucThanhToanRepository.findAllTran(id);
    }

    @Override
    public JsonNode callPaymentApi(HttpServletRequest req, UUID idHoaDon, UUID id, TransactionVnPayRequest transactionVnPayRequest) {
        Optional<TaiKhoan> taiKhoan = khachHangRepository.findById(id);
        Optional<HoaDon> hoaDon = hoaDonRepository.findById(idHoaDon);
        ObjectMapper mapper;
        ObjectNode node = null;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "Thanh toan hoa don";

        String vnp_TxnRef = VnPayConfig.getRandomNumber(8);
        String vnp_IpAddr = VnPayConfig.getIpAddress(req);
        String vnp_TmnCode = VnPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        BigDecimal amount = transactionVnPayRequest.getAmountParam();
        BigDecimal multiplier = new BigDecimal(100);
        vnp_Params.put("vnp_Amount", amount.multiply(multiplier).toString());
        vnp_Params.put("vnp_CurrCode", "VND");
        if ("NCB" != null && !"NCB".isEmpty()) {
            vnp_Params.put("vnp_BankCode", "NCB");
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "OK");

        if ("vn" != null && !"vn".isEmpty()) {
            vnp_Params.put("vnp_Locale", "vn");
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", VnPayConfig.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator iterator = fieldNames.iterator();
        while (iterator.hasNext()) {
            String fieldName = (String) iterator.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                if (iterator.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = VnPayConfig.hmacSHA512(VnPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        System.out.println(hashData.toString());

        mapper = new ObjectMapper();
        node = mapper.createObjectNode();

        // Assuming the payment is successful, continue with saving to the database
        // Make sure to replace 'paymentIsSuccessful' with the actual condition
        boolean paymentIsSuccessful = true;

        if (paymentIsSuccessful) {
            HinhThucThanhToan hinhThucThanhToan = new HinhThucThanhToan();
            hinhThucThanhToan.setId(UUID.randomUUID());
            hinhThucThanhToan.setNgayThanhToan(new Date(System.currentTimeMillis()));
            hinhThucThanhToan.setTaiKhoan(taiKhoan.get());
            hinhThucThanhToan.setTongSoTien(transactionVnPayRequest.getAmountParam());
            hinhThucThanhToan.setPhuongThucThanhToan(2);
            hinhThucThanhToan.setCodeTransaction(VnPayConfig.getRandomNumber(8));
            hinhThucThanhToan.setHoaDon(hoaDon.get());
            hinhThucThanhToan.setTrangThai(2);
            hinhThucThanhToanRepository.save(hinhThucThanhToan);
        }

        node.put("value", VnPayConfig.vnp_PayUrl + "?" + queryUrl);
        return node;
    }
}
