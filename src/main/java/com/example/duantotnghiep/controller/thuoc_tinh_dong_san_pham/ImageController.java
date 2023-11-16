package com.example.duantotnghiep.controller.thuoc_tinh_dong_san_pham;

import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images/")
public class ImageController {

    @Autowired
    private ImageServiceImpl imageService;

    @PostMapping("upload/{sanPhamId}")
    public ResponseEntity<MessageResponse> uploadImage(
            @PathVariable("sanPhamId") UUID sanPhamId,
            @RequestParam MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(imageService.createImage(sanPhamId, file), HttpStatus.CREATED);
    }
}
