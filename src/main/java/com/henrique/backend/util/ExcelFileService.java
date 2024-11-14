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
                verticalPath(sheet, filledLines(sheet) - HEADER_NUMBER);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

     // Function to find out the rows that contain data
    private int filledLines(XSSFSheet sheet) {
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

    // Procedure to read data from the sheet
    private void verticalPath(XSSFSheet sheet, int lastRow) {
        int count = HEADER_NUMBER;

        for (int vertical = 0; vertical < lastRow; vertical++) {
            XSSFRow row = sheet.getRow(count);
            if (row != null) {
                readData(row);
            }
            count++;
        }
    }
    
    private void readData(XSSFRow row) {
        String[] attributes = new String[ROW_NUMBER];
        for (int i = 0; i < ROW_NUMBER; i++) {
            XSSFCell cell = row.getCell(i);
            System.out.println(printCellValue(cell));
        }
    }

    private String printCellValue(XSSFCell cell) {
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "Type not supported";
        };
    }
    
}
