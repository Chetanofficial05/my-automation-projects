package api.utilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XLUtility {

    public static List<Object[]> getExcelData(String filePath, String sheetName) throws IOException {
        List<Object[]> excelData = new ArrayList<>();

        // Load the Excel file using try-with-resources to ensure resources are closed automatically
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next(); // Skip header row

            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();

                String id = getCellValueAsString(row.getCell(0));
                String username = getCellValueAsString(row.getCell(1));
                String firstName = getCellValueAsString(row.getCell(2));
                String lastName = getCellValueAsString(row.getCell(3));
                String email = getCellValueAsString(row.getCell(4));
                String password = getCellValueAsString(row.getCell(5));
                String phone = getCellValueAsString(row.getCell(6));

                // Handle the conversion of id (which might have decimal values) to int
                int userId = convertIdToInt(id);

                // Add the data to the list as an Object array
                excelData.add(new Object[]{userId, username, firstName, lastName, email, password, phone});
            }
        }

        return excelData;
    }

    // Helper method to handle id conversion based on the type (String or Numeric)
    private static int convertIdToInt(String id) {
        try {
            if (id.contains(".")) {
                // If the id contains a decimal (like 34569.0), treat it as a Double and cast to int
                return (int) Double.parseDouble(id);
            } else {
                // If it's just an integer in string form, convert directly to int
                return Integer.parseInt(id);
            }
        } catch (NumberFormatException e) {
            // If conversion fails, log or handle the exception accordingly
            throw new NumberFormatException("Invalid ID format: " + id);
        }
    }

    // Helper method to handle null cells and different cell types
    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Handle date cells
                } else {
                    return String.valueOf(cell.getNumericCellValue()); // Handle numeric cells
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return ""; // Empty cell case
            default:
                return ""; // Fallback for unexpected types
        }
    }
}
