package com.henrique.backend.util;

import java.io.FileInputStream;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public final class ExcelFileService {

    private static final int HEADER_NUMBER = 4;
    private static final int ROW_NUMBER = 9;

    private XSSFSheet sheet;

    public ExcelFileService(String path) {
        openXlsx(path);
    }

    // Method to open a spreadsheet
    public void openXlsx(String path) {
        try(var file =  new FileInputStream(path)) {
            var book = new XSSFWorkbook(file);
            for (int i = 0; i < book.getNumberOfSheets(); i++) {
                sheet = book.getSheetAt(i);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
