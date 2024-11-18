package com.mk.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.Iterator;

public class ExcelUtility {

    public static Object[][] getExcelData(String filePath, String sheetName) throws IOException {
        // Open the Excel file
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        // Get the number of rows and columns
        int rowCount = sheet.getPhysicalNumberOfRows();
        int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

        // Check if there are no rows or columns
        if (rowCount <= 1) {
            throw new IllegalArgumentException("No data found in the sheet or it only contains headers.");
        }

        Object[][] data = new Object[rowCount - 1][colCount];  // -1 because we are skipping the header row

        // Loop through the rows and columns to read data
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();  // Skip header row

        int rowIndex = 0;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Skip completely empty rows
            if (isRowEmpty(row)) {
                continue;
            }

            for (int colIndex = 0; colIndex < colCount; colIndex++) {
                Cell cell = row.getCell(colIndex);

                // Check if the cell is null or empty
                if (cell == null) {
                    data[rowIndex][colIndex] = "";  // Assign a default value, e.g., empty string
                } else {
                    // Handle different cell types
                    switch (cell.getCellType()) {
                        case STRING:
                            data[rowIndex][colIndex] = cell.getStringCellValue();
                            break;
                        case NUMERIC:
                            // If it's a date, we can return it as a string or format it
                            if (DateUtil.isCellDateFormatted(cell)) {
                                data[rowIndex][colIndex] = cell.getDateCellValue().toString();
                            } else {
                                data[rowIndex][colIndex] = String.valueOf(cell.getNumericCellValue());
                            }
                            break;
                        case BOOLEAN:
                            data[rowIndex][colIndex] = String.valueOf(cell.getBooleanCellValue());
                            break;
                        case FORMULA:
                            data[rowIndex][colIndex] = String.valueOf(cell.getCellFormula());
                            break;
                        default:
                            data[rowIndex][colIndex] = "";  // Default to empty string for unknown types
                    }
                }
            }
            rowIndex++;
        }

        workbook.close();
        return data;
    }

    // Helper method to check if the row is empty
    private static boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        for (Cell cell : row) {
            if (cell != null && cell.toString().trim().length() > 0) {
                isEmpty = false;
                break;
            }
        }
        return isEmpty;
    }
}
