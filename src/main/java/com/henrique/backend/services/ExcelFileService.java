package com.henrique.backend.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ExcelFileService {
    
    private Path fileStorageLocation;

    public ExcelFileService(@Value("${file.upload-dir}") String fileUploadDir) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
    }

    public void uploadFileExcel(MultipartFile file) throws Exception {
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var targetLocation = fileStorageLocation.resolve(fileName);
        file.transferTo(targetLocation);

    }
}
