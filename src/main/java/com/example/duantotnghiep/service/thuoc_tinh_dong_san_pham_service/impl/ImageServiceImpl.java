package com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.impl;

import com.example.duantotnghiep.entity.Image;
import com.example.duantotnghiep.entity.SanPham;
import com.example.duantotnghiep.repository.ImageRepository;
import com.example.duantotnghiep.repository.SanPhamRepository;
import com.example.duantotnghiep.response.MessageResponse;
import com.example.duantotnghiep.service.thuoc_tinh_dong_san_pham_service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Override
    @Transactional
    public MessageResponse createImage(UUID sanPhamId, MultipartFile file) throws IOException {
        Optional<SanPham> sanPham = sanPhamRepository.findById(sanPhamId);
        Image image = new Image();
        image.setId(UUID.randomUUID());
        image.setSanPham(sanPham.get()); // Assuming you have a SanPham entity and its constructor
        image.setTrangThai(1);
        image.setIsDefault(false);

        byte[] imageData = file.getBytes();
        String encodedImageData = Base64.getEncoder().encodeToString(imageData);
        image.setTenImage(encodedImageData);

        imageRepository.save(image);
        return MessageResponse.builder().message("Thành công").build();
    }

}
