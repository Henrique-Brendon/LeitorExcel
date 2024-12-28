package com.henrique.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.henrique.backend.services.ExcelFileService;

@RestController
@RequestMapping("api/excel-file")
public class ExcelFileController {

    @Autowired
    private ExcelFileService service;
    
    @PostMapping("upload")
    public String upload(@RequestParam MultipartFile file) throws Exception {
        service.uploadFileExcel(file);
        return "Processamento iniciado!";
    }
}
