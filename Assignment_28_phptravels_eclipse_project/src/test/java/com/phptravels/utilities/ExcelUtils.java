package com.phptravels.utilities;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;

public final class ExcelUtils {

    private ExcelUtils() {
    }

    public static String getCellValue(String filePath, String sheetName, int rowIndex, int cellIndex) {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Sheet sheet = WorkbookFactory.create(fis).getSheet(sheetName);
            Row row = sheet.getRow(rowIndex);
            return row.getCell(cellIndex).toString();
        } catch (IOException e) {
            throw new RuntimeException("Unable to read Excel file: " + filePath, e);
        }
    }
}
