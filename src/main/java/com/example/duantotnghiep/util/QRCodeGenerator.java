package com.example.duantotnghiep.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;


public class QRCodeGenerator {
    public static BufferedImage generateQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        return image;
    }
//public static BufferedImage generateQRCodeImage(String text, int width, int height) {
//    try {
//        // Tạo thông số để tạo mã QR code
//        Map<EncodeHintType, Object> hints = new HashMap<>();
//        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
//        hints.put(EncodeHintType.MARGIN, 1);
//
//        // Tạo BitMatrix từ đoạn text và các thông số
//        BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
//
//        // Tạo ảnh BufferedImage để vẽ mã QR code
//        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        Graphics2D graphics = image.createGraphics();
//
//        // Set nền trắng cho ảnh
//        graphics.setColor(Color.WHITE);
//        graphics.fillRect(0, 0, width, height);
//
//        // Vẽ mã QR code lên ảnh
//        graphics.setColor(Color.BLACK);
//        for (int x = 0; x < width; x++) {
//            for (int y = 0; y < height; y++) {
//                if (bitMatrix.get(x, y)) {
//                    graphics.fillRect(x, y, 1, 1);
//                }
//            }
//        }
//        graphics.dispose();
//
//        return image;
//    } catch (Exception e) {
//        e.printStackTrace();
//        return null;
//    }
//}
}
