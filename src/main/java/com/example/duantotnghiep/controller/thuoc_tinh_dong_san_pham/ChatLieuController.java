package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.entity.DanhMuc;
import com.example.duantotnghiep.request.ChatLieuRequest;
import com.example.duantotnghiep.request.DanhMucRequest;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ChatLieuServiceImpl;
import com.example.duantotnghiep.schedulingtasks.UserExcelExporter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/chat-lieu/")
public class ChatLieuController {

    @Autowired
    private ChatLieuServiceImpl chatLieuService;

    @GetMapping("show")
    public ResponseEntity<List<ChatLieu>> getAllSanPhamGiamGia() {
        return new ResponseEntity<>(chatLieuService.getAll(), HttpStatus.OK);
    }

    @GetMapping("hien-thi")
    public ResponseEntity<List<ChatLieu>> getAllChatLieu(
            @RequestParam(name = "tenChatLieu", required = false) String tenChatLieu,
            @RequestParam(name = "trangThai", required = false) Integer trangThai,
            @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize
    ) {
        return new ResponseEntity<>(chatLieuService.getAllChatLieu(trangThai, tenChatLieu, pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/users/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<ChatLieu> listUsers = chatLieuService.getAll();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @GetMapping("detail")
    public ChatLieu getDanhMucById(@RequestParam UUID id) {
        return chatLieuService.getById(id);
    }

    @PostMapping("create")
    public ResponseEntity<MessageResponse> createDanhMuc(@RequestBody ChatLieuRequest chatLieuRequest) {
        return new ResponseEntity<>(chatLieuService.create(chatLieuRequest), HttpStatus.CREATED);
    }

    @PutMapping("update")
    public ResponseEntity<MessageResponse> updateDanhMuc(@RequestParam UUID id, @RequestBody ChatLieuRequest chatLieuRequest) {
        try {
            MessageResponse response = chatLieuService.update(id, chatLieuRequest);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(MessageResponse.builder().message("Lỗi khi cập nhật").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("delete")
    public ResponseEntity<MessageResponse> deleteDanhMuc(@RequestParam UUID id) {
        return new ResponseEntity<>(chatLieuService.delete(id), HttpStatus.OK);
    }
}
