package com.henrique.backend.util;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.lang.Nullable;

import com.henrique.backend.dtos.ProductDTO;

public final class ExcelFileService implements Serializable, ItemReader<ProductDTO>{

    private static final int HEADER_NUMBER = 4;
    private static final int ROW_NUMBER = 9;
    private int currentIndex = 0;

    private XSSFSheet sheet;
    private List<ProductDTO> productList = new ArrayList<>();

    public ExcelFileService(String path) {
        openXlsx(path);
    }

    // Method to open a spreadsheet
    void openXlsx(String path) {
        try (var file = new FileInputStream(path)) {
            var book = new XSSFWorkbook(file);
            for (int i = 0; i < book.getNumberOfSheets(); i++) {
                sheet = book.getSheetAt(i);
                processSheet(sheet, filledLines(sheet) - HEADER_NUMBER);
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

    // Process each row in the sheet
    void processSheet(XSSFSheet sheet, int lastRow) {
        int count = HEADER_NUMBER;

        for (int i = 0; i < lastRow; i++) {
            XSSFRow row = sheet.getRow(count);
            if (row != null) {
                ProductDTO product = readProductData(row);
                if (product != null) {
                    productList.add(product);
                }
            }
            count++;
        }
    }

    ProductDTO readProductData(XSSFRow row) {
        try {
            String[] attributes = {
                getCellValue(row.getCell(0)),
                getCellValue(row.getCell(1)),
                getCellValue(row.getCell(2)),
                getCellValue(row.getCell(3)),
                getCellValue(row.getCell(4)),
                getCellValue(row.getCell(5)),
                getCellValue(row.getCell(6)),
                getCellValue(row.getCell(7))
            };

            double quantity = Double.parseDouble(getCellValue(row.getCell(8)));

            // Added the product to the list the number of times indicated by the quantity
            List<ProductDTO> products = new ArrayList<>((int) quantity);
            for (int i = 0; i < quantity; i++) {
                products.add(new ProductDTO(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4],
                attributes[5], attributes[6], attributes[7]));
            }
            productList.addAll(products);

        } catch (Exception e) {
            System.err.println("Error reading row data: " + e.getMessage());
            return null;
        }
        return null;
    }

    String getCellValue(XSSFCell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case FORMULA -> cell.getCellFormula();
            default -> "Type not supported";
        };
    }

    @Override
    @Nullable
    public ProductDTO read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (currentIndex != productList.size()) {
            ProductDTO product = productList.get(currentIndex);
            currentIndex++;
            return product;
        }
        return null;
    }

}
