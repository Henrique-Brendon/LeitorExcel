package com.henrique.backend.util;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
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
                System.out.println(filledLines(sheet));
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

     // Function to find out the rows that contain data
    int filledLines(XSSFSheet sheet) {
        int filledRowsCount = 0;

        // Iterate over all rows up to the last row with content
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow currentRow = sheet.getRow(i);
            if (currentRow != null) {
                boolean isRowFilled = false;
                for (int j = 0; j < ROW_NUMBER; j++) {
                    XSSFCell cell = currentRow.getCell(j);
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowFilled = true;
                        break;
                    }
                }
                if (isRowFilled) {
                    filledRowsCount++;
                } else if (filledRowsCount > 0) {
                    break;
                }
            }
        }
        return filledRowsCount;
    }
}
