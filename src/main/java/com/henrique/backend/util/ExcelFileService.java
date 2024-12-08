package com.henrique.backend.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.compress.archivers.dump.InvalidFormatException;
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

import com.henrique.backend.dtos.ExcelDTO;
import com.henrique.backend.util.exceptions.ExcelFileException;

public final class ExcelFileService implements Serializable, ItemReader<ExcelDTO>{

    private static final int HEADER_NUMBER = 4;
    private static final int ROW_NUMBER = 9;
    private int currentIndex = 0;

    private XSSFSheet sheet;
    private List<ExcelDTO> itemExcelList = new ArrayList<>();

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
        } catch (InvalidFormatException e) {
            throw new ExcelFileException("Wrong file format for file: " + path);
        }
        catch (IOException e) {
            throw new ExcelFileException("Failed to open file");
        }
        catch (Exception e) {
            throw new ExcelFileException("Error");
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
        try {
            for (int i = 0; i < lastRow; i++) {
                XSSFRow row = sheet.getRow(count);
                
                if (row != null) {
                    ExcelDTO itemDTO = readProductData(row);
                    if (itemDTO != null) {
                        itemExcelList.add(itemDTO);
                    }
                }
                count++;
            }
            
        } catch(NullPointerException e) {
            throw new ExcelFileException("Missing or null cell");
        }

    }

    ExcelDTO readProductData(XSSFRow row) {
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
            List<ExcelDTO> itemDTO = new ArrayList<>((int) quantity);
            for (int i = 0; i < quantity; i++) {
                itemDTO.add(new ExcelDTO(attributes[0], attributes[1], attributes[2], attributes[3], attributes[4],
                attributes[5], attributes[6], attributes[7]));
            }
            itemExcelList.addAll(itemDTO);

        } catch (NumberFormatException e) {
            throw new ExcelFileException("Error reading row data");
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
    public ExcelDTO read() {
        try {
            if (currentIndex != itemExcelList.size()) {
                ExcelDTO itemDTO = itemExcelList.get(currentIndex);
                currentIndex++;
                return itemDTO;
            }
            return null;
        } catch(IndexOutOfBoundsException e) {
            throw new ExcelFileException("Error: Index is outside the list bounds");
        } catch(ParseException e) {
            throw new ExcelFileException("Error when trying to parse a value");
        } catch(UnexpectedInputException e) {
            throw new ExcelFileException("Unexpected error while processing input data from Excel File");
        } catch(NonTransientResourceException e) {
            throw new ExcelFileException("Error acessing resource required to process Excel file");
        }
    }

}
