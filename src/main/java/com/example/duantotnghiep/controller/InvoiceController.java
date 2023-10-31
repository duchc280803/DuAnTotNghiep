package com.example.duantotnghiep.controller;

import com.example.duantotnghiep.entity.ChatLieu;
import com.example.duantotnghiep.repository.ChatLieuRepository;
import com.example.duantotnghiep.service.IExcelDataService;
import com.example.duantotnghiep.service.IFileUploaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@RestController
@RequestMapping("/api/v1/invoice/")
public class InvoiceController {
    @Autowired
    IFileUploaderService fileService;

    @Autowired
    IExcelDataService excelservice;

    @Autowired
    ChatLieuRepository repo;

    @GetMapping("/")
    public String index() {
        return "uploadPage";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        fileService.uploadFile(file);

        redirectAttributes.addFlashAttribute("message",
                "You have successfully uploaded '"+ file.getOriginalFilename()+"' !");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "sucees";
    }

    @GetMapping("/saveData")
    public String saveExcelData(Model model) {

        List<ChatLieu> excelDataAsList = excelservice.getExcelDataAsList();
        int noOfRecords = excelservice.saveExcelData(excelDataAsList);
        model.addAttribute("noOfRecords",noOfRecords);
        return "success";
    }
}
