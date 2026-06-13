package com.seleniumtestng.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtil {

    private ExcelUtil() {}

    public static Object[][] getData(String filePath, String sheetName) {
        List<Object[]> rows = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new RuntimeException("Sheet '" + sheetName + "' not found in " + filePath);
            }

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;

                boolean allEmpty = true;
                Object[] cells = new Object[row.getLastCellNum()];
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    cells[j] = getCellValue(cell);
                    if (cells[j] != null && !cells[j].toString().isBlank()) {
                        allEmpty = false;
                    }
                }
                if (!allEmpty) {
                    rows.add(cells);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read Excel file: " + filePath, e);
        }

        return rows.toArray(Object[][]::new);
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) return "";

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                double val = cell.getNumericCellValue();
                if (val == Math.floor(val) && !Double.isInfinite(val)) {
                    return String.valueOf((long) val);
                }
                return String.valueOf(val);
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                try {
                    return String.valueOf(cell.getNumericCellValue());
                } catch (Exception e) {
                    try {
                        return cell.getStringCellValue();
                    } catch (Exception e2) {
                        return cell.getCellFormula();
                    }
                }
            default:
                return "";
        }
    }
}
